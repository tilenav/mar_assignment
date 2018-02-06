package com.example.xmlprocessor.repository;

import com.example.xmlprocessor.model.Department;
import com.example.xmlprocessor.model.Doctor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void testCreateDoctor() {
        // Test, ki gre na bazo
        Department department = new Department("marand");
        Doctor doctor = new Doctor();

        doctor.setDepartment(department);
        doctor.setId(200L);
        // Save to DB
        doctorRepository.save(doctor);

        // Retrieve from DB
        Doctor retrievedDoc = doctorRepository.findOne(200L);

        Assert.assertNotNull(retrievedDoc);
    }
}
