package com.example.xmlprocessor.controller;


import com.example.xmlprocessor.exception.NoResultsException;
import com.example.xmlprocessor.model.Patient;
import com.example.xmlprocessor.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> listAllPatients() throws NoResultsException {
        logger.info("Fetching all patients.");
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            logger.info("No patients found.");
            throw new NoResultsException("No patients found.");
        }
        return patients;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatient(@PathVariable("id") long id) throws NoResultsException {
        logger.info("Fetching Patient with id {}", id);
        Patient patient = patientRepository.findOne(id);
        if (patient == null) {
            logger.info("Patient with id {} not found.", id);
            throw new NoResultsException("Patient with id " + id + " not found.");
        }
        return patient;
    }
}
