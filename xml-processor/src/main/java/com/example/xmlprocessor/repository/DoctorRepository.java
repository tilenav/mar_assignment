package com.example.xmlprocessor.repository;

import com.example.xmlprocessor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
