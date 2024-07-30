package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lead_id")
  private Employee lead;

  @OneToMany(mappedBy = "team")
  private Set<Employee> employees = new LinkedHashSet<>();

  public Team() {
  }

  public Team(String name) {
    this.name = name;
  }

  public Team(String name, Employee employee) {
    this.name = name;
    this.lead = employee;
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

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    Team team = (Team) o;
    return getName().equals(team.getName());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getName().hashCode();
    return result;
  }
}