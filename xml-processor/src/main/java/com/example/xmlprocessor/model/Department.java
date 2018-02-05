package com.example.xmlprocessor.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "department")
@EntityListeners(AuditingEntityListener.class)
public class Department {

  @Id
  @JacksonXmlProperty(isAttribute = true)
  private String name;

  @OneToMany(mappedBy = "department")
  @Cascade(CascadeType.ALL)
  private Set<Doctor> doctors;

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Doctor> getDoctors() {
    return doctors;
  }

  public void setDoctors(Set<Doctor> doctors) {
    this.doctors = doctors;
  }
}
