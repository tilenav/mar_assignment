package com.example.xmlprocessor.controller;


import com.example.xmlprocessor.model.Patient;
import com.example.xmlprocessor.repository.PatientRepository;
import com.example.xmlprocessor.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/")
    public ResponseEntity<List<Patient>> listAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // Could use HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching Patient with id {}", id);
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            logger.error("Patient with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Patient with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Patient>(patient, HttpStatus.OK);
    }
}
