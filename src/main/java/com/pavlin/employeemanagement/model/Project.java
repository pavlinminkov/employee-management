package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "project")
public class Project extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Size(max = 255)
  @NotNull
  @Column(name = "description", nullable = false)
  private String description;

  public Project() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Project project = (Project) o;
    return name.equals(project.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}