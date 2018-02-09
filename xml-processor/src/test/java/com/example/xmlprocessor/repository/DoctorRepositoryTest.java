package com.example.xmlprocessor.repository;

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
    public void testSave() {
        // Test, ki gre na bazo / unit test (?)
        Doctor doctor = new Doctor();

        // Save to DB
        doctor = doctorRepository.save(doctor);

        // Retrieve from DB
        Doctor retrievedDoc = doctorRepository.findOne(doctor.getId());

        Assert.assertNotNull(retrievedDoc);
    }
}
