package com.example.xmlprocessor.repository;

import com.example.xmlprocessor.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
