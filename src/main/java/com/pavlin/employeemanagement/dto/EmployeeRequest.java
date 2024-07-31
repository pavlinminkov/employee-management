package com.pavlin.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Employee}
 */
public class EmployeeRequest implements Serializable {

  @Size(max = 255)
  @NotBlank
  private final String firstName;
  @Size(max = 255)
  @NotBlank
  private final String middleName;
  @Size(max = 255)
  @NotBlank
  private final String lastName;
  @Size(min = 4, max = 255)
  @NotBlank
  private final String username;
  @Size(min = 8, max = 255)
  @NotBlank
  private final String password;
  @Size(max = 255)
  @Email
  @NotBlank
  private final String email;
  @NotNull
  private final UUID teamId;

  public EmployeeRequest(String firstName, String middleName, String lastName,
      String username,
      String password, String email, UUID teamId) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.teamId = teamId;
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

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public UUID getTeamId() {
    return teamId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeRequest entity = (EmployeeRequest) o;
    return Objects.equals(this.firstName, entity.firstName) &&
        Objects.equals(this.middleName, entity.middleName) &&
        Objects.equals(this.lastName, entity.lastName) &&
        Objects.equals(this.username, entity.username) &&
        Objects.equals(this.password, entity.password) &&
        Objects.equals(this.email, entity.email) &&
        Objects.equals(this.teamId, entity.teamId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, middleName, lastName, username, password, email, teamId);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "firstName = " + firstName + ", " +
        "middleName = " + middleName + ", " +
        "lastName = " + lastName + ", " +
        "username = " + username + ", " +
        "password = " + password + ", " +
        "email = " + email + ", " +
        "teamTeamId = " + teamId + ")";
  }
}