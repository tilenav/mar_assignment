package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.exception.NoResultsException;
import com.example.xmlprocessor.model.Disease;
import com.example.xmlprocessor.repository.DiseaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disease")
public class DiseaseController {

    private static final Logger logger = LoggerFactory.getLogger(DiseaseController.class);

    @Autowired
    DiseaseRepository diseaseRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Disease> listAllDiseases() throws NoResultsException {
        logger.info("Fetching all diseases.");
        List<Disease> diseases = diseaseRepository.findAll();
        if (diseases.isEmpty()) {
            logger.info("No diseases found.");
            throw new NoResultsException("No diseases found.");
        }
        return diseases;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Disease getDisease(@PathVariable("name") String name) throws NoResultsException {
        logger.info("Fetching Disease with name {}", name);
        Disease disease = diseaseRepository.findOne(name);
        diseaseRepository.findOne(name);
        if (disease == null) {
            logger.info("Disease with name {} not found.", name);
            throw new NoResultsException("Disease with name " + name + " not found");
        }
        return disease;
    }
}
