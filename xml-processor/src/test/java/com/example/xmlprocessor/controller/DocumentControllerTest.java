package com.example.xmlprocessor.controller;

import com.example.xmlprocessor.model.Department;
import com.example.xmlprocessor.model.Disease;
import com.example.xmlprocessor.model.Doctor;
import com.example.xmlprocessor.model.Patient;
import com.example.xmlprocessor.repository.DepartmentRepository;
import com.example.xmlprocessor.repository.DiseaseRepository;
import com.example.xmlprocessor.repository.DoctorRepository;
import com.example.xmlprocessor.repository.PatientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DocumentControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_XML.getType(),
            MediaType.APPLICATION_XML.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private List<Disease> diseases = new ArrayList<>();

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // We want a clean start.
        // We could also do this by importing our script with 0 entries (or with diseases). @Sql annotation
        // Would probably be better, because then we would not need all the repositories.
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
        diseaseRepository.deleteAll();
        departmentRepository.deleteAll();

        this.diseases.add(diseaseRepository.save(new Disease("long_legs")));
        this.diseases.add(diseaseRepository.save(new Disease("nice_to_people")));
        this.diseases.add(diseaseRepository.save(new Disease("used_to_have_dredds")));
        this.diseases.add(diseaseRepository.save(new Disease("chocaholic")));
        this.diseases.add(diseaseRepository.save(new Disease("great_haircut")));
    }

    @Test
    public void testDocumentProcessing() throws Exception {
        // Test ki zažene server in pokliče API
        String inputXml = createInputXML();
        this.mockMvc.perform(post("/document/")
                .content(inputXml)
                .contentType(contentType))
                .andExpect(status().isCreated());
    }

    private String createInputXML() throws JsonProcessingException {
        Disease d1 = new Disease("long_legs");
        Disease d2 = new Disease("nice_to_people");
        Disease d3 = new Disease("used_to_have_dredds");
        Disease d4 = new Disease("chocaholic");
        Disease d5 = new Disease("great_haircut");

        Set<Disease> diseases1 = new HashSet<Disease>();
        diseases1.add(d1);
        diseases1.add(d2);
        Patient p1 = new Patient(1L, "Bostjan", "Lah", diseases1);

        Set<Disease> diseases2 = new HashSet<Disease>();
        diseases2.add(d3);
        diseases2.add(d2);
        Patient p2 = new Patient(2L, "Boris", "Marn", diseases2);

        Set<Disease> diseases3 = new HashSet<Disease>();
        diseases3.add(d4);
        diseases3.add(d5);
        Patient p3 = new Patient(3L, "Anze", "Droljc", diseases3);

        Department department = new Department("marand");

        Set<Patient> patients = new HashSet<Patient>();
        patients.add(p1);
        patients.add(p2);
        patients.add(p3);
        Doctor doctor = new Doctor(100L, department, patients);

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(doctor);

        return xml;
    }
}
