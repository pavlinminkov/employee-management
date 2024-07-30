package com.pavlin.employeemanagement.model;

import com.pavlin.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Size(max = 255)
  @NotNull
  @Column(name = "middle_name", nullable = false)
  private String middleName;

  @Size(max = 255)
  @NotNull
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Size(max = 255)
  @NotNull
  @Column(name = "username", nullable = false)
  private String username;

  @Size(max = 255)
  @NotNull
  @Column(name = "password", nullable = false)
  private String password;

  @Size(max = 255)
  @NotNull
  @Column(name = "email", nullable = false)
  private String email;

  @NotNull
  @Column(name = "is_enabled", nullable = false)
  private Boolean isEnabled = false;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  @ManyToMany
  @JoinTable(name = "employee_role",
      joinColumns = @JoinColumn(name = "employee_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new LinkedHashSet<>();

  @OneToMany(mappedBy = "employee")
  private Set<Leave> leaves = new LinkedHashSet<>();

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<Leave> getLeaves() {
    return leaves;
  }

  public void setLeaves(Set<Leave> leaves) {
    this.leaves = leaves;
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

    Employee employee = (Employee) o;
    return getUsername().equals(employee.getUsername()) && getEmail().equals(employee.getEmail());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getUsername().hashCode();
    result = 31 * result + getEmail().hashCode();
    return result;
  }
}