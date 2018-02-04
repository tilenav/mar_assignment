package com.example.xmlprocessor.repository;

import com.example.xmlprocessor.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
