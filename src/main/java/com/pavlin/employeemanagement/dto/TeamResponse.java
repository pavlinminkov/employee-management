package com.pavlin.employeemanagement.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Team}
 */
public record TeamResponse(UUID id, String name, UUID leadId) implements Serializable {

}