package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.exception.EntityExistsException;
import com.example.xmlprocessor.exception.WhileSavingException;
import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.model.DocumentReport;
import com.example.xmlprocessor.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DocumentReportRepository documentReportRepository;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentReport processDocument(@Valid @RequestBody Doctor doctor, UriComponentsBuilder ucBuilder)
            throws EntityExistsException, WhileSavingException {
        logger.info("Creating doctor : {}", doctor);

        DocumentReport report = new DocumentReport(doctor.getId());

        if (doctorRepository.exists(doctor.getId())) {
            logger.info("Unable to process document. A doctor with id {} already exists.", doctor.getId());
            report.setError(String.format("Unable to process document. A doctor with id %d already exists.",
                    doctor.getId()));
            documentReportRepository.save(report);

            throw new EntityExistsException("Unable to create. A doctor with id " +
                    doctor.getId() + " already exist.");
        }

        try {
            doctorRepository.save(doctor);
        } catch (Exception e) {
            // We should catch specific exceptions, but it will be like this for now.
            logger.error("Unable to save objects to DB. Exception cause: {}", e.getCause().toString());
            logger.error("Stacktrace: {}", e);
            report.setError(String.format("Unable to save objects to DB. Exception cause: %s",
                    e.getCause().toString()));
            documentReportRepository.save(report);

            throw new WhileSavingException("Unable to save objects to database.");
        }
        report.setError("No error while processing XML document.");
        logger.info("XML document correctly processed and objects were stored in DB.");
        documentReportRepository.save(report);

        return report;
    }

}
