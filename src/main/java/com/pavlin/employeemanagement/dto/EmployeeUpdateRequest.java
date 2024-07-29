package com.pavlin.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Employee}
 */
public class EmployeeUpdateRequest implements Serializable {

  @Size(max = 255)
  @NotBlank
  private final String firstName;
  @Size(max = 255)
  @NotBlank
  private final String middleName;
  @Size(max = 255)
  @NotBlank
  private final String lastName;
  @NotNull
  private final UUID teamTeamId;

  public EmployeeUpdateRequest(String firstName, String middleName, String lastName, UUID teamTeamId) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.teamTeamId = teamTeamId;
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

  public UUID getTeamTeamId() {
    return teamTeamId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeUpdateRequest entity = (EmployeeUpdateRequest) o;
    return Objects.equals(this.firstName, entity.firstName) &&
        Objects.equals(this.middleName, entity.middleName) &&
        Objects.equals(this.lastName, entity.lastName) &&
        Objects.equals(this.teamTeamId, entity.teamTeamId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, middleName, lastName, teamTeamId);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "firstName = " + firstName + ", " +
        "middleName = " + middleName + ", " +
        "lastName = " + lastName + ", " +
        "teamTeamId = " + teamTeamId + ")";
  }
}