package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.exception.NoResultsException;
import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Doctor> listAllDoctors() throws NoResultsException {
        logger.info("Fetching all doctors.");
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            logger.info("No doctors found.");
            throw new NoResultsException("No doctors found.");
        }
        return doctors;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Doctor getDoctor(@PathVariable("id") long id) throws NoResultsException {
        logger.info("Fetching Doctor with id {}", id);
        Doctor doctor = doctorRepository.findOne(id);
        if (doctor == null) {
            logger.info("Doctor with id {} not found.", id);
            throw new NoResultsException("Doctor with id " + id + " not found");
        }
        return doctor;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDoctor(@PathVariable("id") long id) throws NoResultsException {
        logger.info("Fetching Doctor with id {}", id);
        Doctor doctor = doctorRepository.findOne(id);
        if (doctor == null) {
            logger.info("Doctor with id {} not found.", id);
            throw new NoResultsException("Doctor with id " + id + " not found");
        }
        doctorRepository.delete(id);
    }
}
