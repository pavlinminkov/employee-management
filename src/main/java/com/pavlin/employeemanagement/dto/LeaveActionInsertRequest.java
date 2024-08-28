package com.pavlin.employeemanagement.dto;

import com.pavlin.employeemanagement.model.common.LeaveActionType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.LeaveAction}
 */
public record LeaveActionInsertRequest(@NotNull LeaveActionType type,
                                       @NotNull UUID leaveId,
                                       @NotNull UUID employeeId) implements Serializable {

}