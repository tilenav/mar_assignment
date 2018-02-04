package com.example.xmlprocessor.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disease")
@EntityListeners(AuditingEntityListener.class)
public class Disease {

  @Id
  private String name;

  @ManyToMany(mappedBy = "diseases")
  @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE})
  private Set<Patient> patients;

  public Disease() {
  }

  public Disease(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Patient> getPatients() {
    return patients;
  }

  public void setPatients(Set<Patient> patients) {
    this.patients = patients;
  }
}
