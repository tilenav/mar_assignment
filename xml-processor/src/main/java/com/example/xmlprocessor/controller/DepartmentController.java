package com.example.xmlprocessor.controller;


import com.example.xmlprocessor.exception.NoResultsException;
import com.example.xmlprocessor.model.Department;
import com.example.xmlprocessor.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Department> listAllDepartments() throws NoResultsException {
        logger.info("Fetching all departments.");
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            logger.info("No departments found.");
            throw new NoResultsException("No departments found.");
        }
        return departments;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Department getDepartment(@PathVariable("name") String name) throws NoResultsException {
        logger.info("Fetching Department with name {}", name);
        Department department = departmentRepository.findOne(name);
        if (department == null) {
            logger.info("Department with name {} not found.", name);
            throw new NoResultsException("Department with name " + name + " not found");
        }
        return department;
    }
}
