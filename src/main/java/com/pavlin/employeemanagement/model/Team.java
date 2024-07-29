package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "team")
public class Team extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lead_id")
  private Employee lead;

  public Team() {
  }

  public Team(String name) {
    this.name = name;
  }

  public Team(String name, Employee lead) {
    this.name = name;
    this.lead = lead;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee getLead() {
    return lead;
  }

  public void setLead(Employee lead) {
    this.lead = lead;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Team team = (Team) o;
    return name.equals(team.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}