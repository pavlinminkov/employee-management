package com.pavlin.employeemanagement.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Employee}
 */
public class EmployeeResponse implements Serializable {

  private final UUID id;
  private final String firstName;
  private final String middleName;
  private final String lastName;
  private final String email;
  private final TeamResponse team;

  public EmployeeResponse(UUID id, String firstName, String middleName, String lastName,
      String email, TeamResponse team) {
    this.id = id;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.email = email;
    this.team = team;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public TeamResponse getTeam() {
    return team;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeResponse entity = (EmployeeResponse) o;
    return Objects.equals(this.id, entity.id) &&
        Objects.equals(this.firstName, entity.firstName) &&
        Objects.equals(this.middleName, entity.middleName) &&
        Objects.equals(this.lastName, entity.lastName) &&
        Objects.equals(this.email, entity.email) &&
        Objects.equals(this.team, entity.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, middleName, lastName, email, team);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "firstName = " + firstName + ", " +
        "middleName = " + middleName + ", " +
        "lastName = " + lastName + ", " +
        "userEmail = " + email + ", " +
        "team = " + team + ")";
  }
}