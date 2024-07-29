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
public class EmployeeInsertRequest implements Serializable {

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
  private final UUID userUserId;
  @NotNull
  private final UUID teamTeamId;

  public EmployeeInsertRequest(String firstName, String middleName, String lastName,
      UUID userUserId,
      UUID teamTeamId) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.userUserId = userUserId;
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

  public UUID getUserUserId() {
    return userUserId;
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
    EmployeeInsertRequest entity = (EmployeeInsertRequest) o;
    return Objects.equals(this.firstName, entity.firstName) &&
        Objects.equals(this.middleName, entity.middleName) &&
        Objects.equals(this.lastName, entity.lastName) &&
        Objects.equals(this.userUserId, entity.userUserId) &&
        Objects.equals(this.teamTeamId, entity.teamTeamId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, middleName, lastName, userUserId, teamTeamId);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "firstName = " + firstName + ", " +
        "middleName = " + middleName + ", " +
        "lastName = " + lastName + ", " +
        "userUserId = " + userUserId + ", " +
        "teamTeamId = " + teamTeamId + ")";
  }
}