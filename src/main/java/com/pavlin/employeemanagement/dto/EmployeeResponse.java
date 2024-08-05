package com.pavlin.employeemanagement.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Employee}
 */
public record EmployeeResponse(UUID id,
                               String firstName,
                               String middleName,
                               String lastName,
                               String email,
                               TeamResponse team) implements Serializable {

}