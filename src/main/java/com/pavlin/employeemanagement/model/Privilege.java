package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "privilege")
public class Privilege extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

    Privilege privilege = (Privilege) o;
    return getName().equals(privilege.getName());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getName().hashCode();
    return result;
  }
}