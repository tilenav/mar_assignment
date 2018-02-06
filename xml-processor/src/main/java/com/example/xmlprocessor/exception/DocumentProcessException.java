package com.example.xmlprocessor.exception;

import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.model.DocumentReport;
import com.example.xmlprocessor.repository.DocumentReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DocumentProcessException extends Exception {

    @Autowired
    DocumentReportRepository documentReportRepository;

    public DocumentProcessException(String s, Doctor doctor) {
        super(s);

        // This logging could probably be done with an interceptor.
        if (doctor.getId() != null) {
            DocumentReport report = new DocumentReport(doctor.getId());
            report.setError(s);

            documentReportRepository.save(report);
        }
    }
}
