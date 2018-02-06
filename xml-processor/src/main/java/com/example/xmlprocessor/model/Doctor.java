package com.example.xmlprocessor.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Set;

@Entity
@Table(name = "doctor")
@EntityListeners(AuditingEntityListener.class)
public class Doctor {

  @Id
  @GeneratedValue(generator = "customGenerator")
  @GenericGenerator(name="customGenerator", strategy = "com.example.xmlprocessor.idgenerator.IdOrGenerated")
  private Long id;

  @ManyToOne
  @Cascade(CascadeType.ALL)
  @JoinColumn(name = "department_name")
  @JsonIgnoreProperties("doctors")
  private Department department;

  @ManyToMany
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
  @JoinTable(name = "doctor_has_patient", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
  @JacksonXmlProperty(localName = "patient")
  @JacksonXmlElementWrapper(localName = "patients")
  @JsonIgnoreProperties("doctors")
  private Set<Patient> patients;

  public Doctor() {
  }

  public Doctor(Department department, Set<Patient> patients) {
    this.department = department;
    this.patients = patients;
  }

  public Doctor(Long id, Department department, Set<Patient> patients) {
    this.id = id;
    this.department = department;
    this.patients = patients;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Set<Patient> getPatients() {
    return patients;
  }

  public void setPatients(Set<Patient> patients) {
    this.patients = patients;
  }
}
