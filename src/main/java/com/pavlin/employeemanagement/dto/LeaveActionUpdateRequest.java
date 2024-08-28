package com.pavlin.employeemanagement.dto;

import com.pavlin.employeemanagement.model.common.LeaveActionType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.LeaveAction}
 */
public record LeaveActionUpdateRequest(@NotNull LeaveActionType type) implements Serializable {

}