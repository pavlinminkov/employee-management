package com.pavlin.employeemanagement.dto;

import com.pavlin.employeemanagement.model.common.LeaveType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Leave}
 */
public record LeaveInsertRequest(@NotNull @Future LocalDate startDate,
                                 @NotNull @Future LocalDate endDate,
                                 @NotNull LeaveType type,
                                 @NotNull UUID employeeId) implements Serializable {

}