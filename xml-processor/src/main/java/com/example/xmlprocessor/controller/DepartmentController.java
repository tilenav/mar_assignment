package com.example.xmlprocessor.controller;


import com.example.xmlprocessor.model.Department;
import com.example.xmlprocessor.repository.DepartmentRepository;
import com.example.xmlprocessor.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/")
    public ResponseEntity<List<Department>> listAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // Could use HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("name") String name) {
        logger.info("Fetching Department with name {}", name);
        Department dis = new Department(name);
        Department department = departmentRepository.findByName(name);
        if (department == null) {
            logger.error("Department with name {} not found.", name);
            return new ResponseEntity(new CustomErrorType("Department with name " + name
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Department>(department, HttpStatus.OK);
    }
}
