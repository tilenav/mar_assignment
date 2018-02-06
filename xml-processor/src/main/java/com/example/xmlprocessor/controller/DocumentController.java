package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.exception.DocumentProcessException;
import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.model.DocumentReport;
import com.example.xmlprocessor.repository.DoctorRepository;
import com.example.xmlprocessor.repository.DocumentReportRepository;
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
    public void processDocument(@Valid @RequestBody Doctor doctor, UriComponentsBuilder ucBuilder)
            throws DocumentProcessException {
        logger.info("Creating doctor : {}", doctor);

        if (doctor.getId() != null) {
            if (doctorRepository.exists(doctor.getId())) {
                logger.info("Unable to process document. A doctor with id {} already exists.", doctor.getId());
                throw new DocumentProcessException("Unable to create. A doctor with id " +
                        doctor.getId() + " already exist.", doctor);
            }
        }

        try {
            doctorRepository.save(doctor);
        } catch (Exception e) {
            // We should catch specific exceptions, but it will be like this for now.
            logger.error("Unable to save objects to DB. Exception cause: {}", e.getCause().toString());
            logger.error("Stacktrace: {}", e);

            throw new DocumentProcessException(String.format("Unable to save objects to DB. Exception cause: %s",
                    e.getCause().toString()), doctor);
        }

        logger.info("XML document correctly processed and objects were stored in DB.");

        // As written in DocumentProcessException class, this could probably be done with an interceptor.
        DocumentReport report = new DocumentReport(doctor.getId());
        report.setError("XML document correctly processed and objects were stored in DB.");
        documentReportRepository.save(report);
    }

}
