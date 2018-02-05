package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.repository.DoctorRepository;
import com.example.xmlprocessor.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping("/")
    public ResponseEntity<List<Doctor>> listAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // Could use HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
