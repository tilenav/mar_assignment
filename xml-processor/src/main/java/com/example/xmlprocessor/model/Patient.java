package com.example.xmlprocessor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "patient")
@EntityListeners(AuditingEntityListener.class)
public class Patient {

  @Id
  @GeneratedValue(generator = "customGenerator")
  @GenericGenerator(name="customGenerator", strategy = "com.example.xmlprocessor.idgenerator.IdOrGenerated")
  private Long id;

  @NotBlank
  @JacksonXmlProperty(localName = "first_name")
  private String firstName;

  @NotBlank
  @JacksonXmlProperty(localName = "last_name")
  private String lastName;

  @ManyToMany
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
  @JoinTable(name = "patient_has_disease", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "disease_name", referencedColumnName = "name"))
  @JacksonXmlProperty(localName = "diseases")
  @JsonIgnoreProperties("patients")
  private Set<Disease> diseases;

  @ManyToMany(mappedBy = "patients")
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
  @JsonIgnoreProperties("patients")
  private Set<Doctor> doctors;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<Disease> getDiseases() {
    return diseases;
  }

  public void setDiseases(Set<Disease> diseases) {
    this.diseases = diseases;
  }

  public Set<Doctor> getDoctors() {
    return doctors;
  }

  public void setDoctors(Set<Doctor> doctors) {
    this.doctors = doctors;
  }
}
