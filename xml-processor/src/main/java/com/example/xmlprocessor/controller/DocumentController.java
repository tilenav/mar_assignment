package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.model.DocumentReport;
import com.example.xmlprocessor.repository.*;
import com.example.xmlprocessor.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DocumentReportRepository documentReportRepository;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> processDocument(@Valid @RequestBody Doctor doctor, UriComponentsBuilder ucBuilder) {
        logger.info("Creating doctor : {}", doctor);

        DocumentReport report = new DocumentReport(doctor.getId());

        if (doctorRepository.exists(doctor.getId())) {
            logger.error("Unable to process document. A doctor with id {} already exists.", doctor.getId());
            report.setError(String.format("Unable to process document. A doctor with id %d already exists.",
                    doctor.getId()));
            documentReportRepository.save(report);

            return new ResponseEntity(new CustomErrorType("Unable to create. A doctor with id " +
                    doctor.getId() + " already exist."), HttpStatus.CONFLICT);
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

            return new ResponseEntity(new CustomErrorType("Unable to save objects to database." +
                    ""), HttpStatus.CONFLICT);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/doctor/{id}").buildAndExpand(doctor.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/doctor/")
    public ResponseEntity<List<Doctor>> listAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // Could use HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching Doctor with id {}", id);
        Doctor doctor = doctorRepository.findOne(id);
        if (doctor == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Doctor with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
    }

}
