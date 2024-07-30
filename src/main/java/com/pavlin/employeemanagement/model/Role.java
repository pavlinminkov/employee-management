package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(name = "role_privilege",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "privilege_id"))
  private Set<Privilege> privileges = new LinkedHashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Privilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Set<Privilege> privileges) {
    this.privileges = privileges;
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

    Role role = (Role) o;
    return getName().equals(role.getName());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getName().hashCode();
    return result;
  }
}