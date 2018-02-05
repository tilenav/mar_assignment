package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.model.Disease;
import com.example.xmlprocessor.repository.DiseaseRepository;
import com.example.xmlprocessor.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disease")
public class DiseaseController {

    private static final Logger logger = LoggerFactory.getLogger(DiseaseController.class);

    @Autowired
    DiseaseRepository diseaseRepository;

    @GetMapping("/")
    public ResponseEntity<List<Disease>> listAllDiseases() {
        List<Disease> diseases = diseaseRepository.findAll();
        if (diseases.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // Could use HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Disease>>(diseases, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("name") String name) {
        logger.info("Fetching Disease with name {}", name);
        Disease dis = new Disease(name);
        Disease disease = diseaseRepository.findByName(name);
        if (disease == null) {
            logger.error("Department with name {} not found.", name);
            return new ResponseEntity(new CustomErrorType("Disease with name " + name
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Disease>(disease, HttpStatus.OK);
    }
}
