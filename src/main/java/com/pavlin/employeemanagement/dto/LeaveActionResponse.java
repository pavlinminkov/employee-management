package com.pavlin.employeemanagement.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.LeaveAction}
 */
public record LeaveActionResponse(UUID id,
                                  LocalDate date,
                                  UUID leaveId,
                                  EmployeeDetails employee) implements Serializable {

  /**
   * DTO for {@link com.pavlin.employeemanagement.model.Employee}
   */
  public record EmployeeDetails(UUID id,
                                String firstName,
                                String middleName,
                                String lastName,
                                String email) implements Serializable {

  }
}