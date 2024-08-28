package com.pavlin.employeemanagement.mapper;

import com.pavlin.employeemanagement.dto.LeaveInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveResponse;
import com.pavlin.employeemanagement.dto.LeaveUpdateRequest;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Leave;
import com.pavlin.employeemanagement.model.common.LeaveState;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper {

  public LeaveResponse toLeaveResponse(Leave leave) {
    Objects.requireNonNull(leave);

    return new LeaveResponse(
        leave.getId(),
        leave.getRequestDate(),
        leave.getStartDate(),
        leave.getEndDate(),
        leave.getState(),
        leave.getType(),
        new LeaveResponse.EmployeeDetails(
            leave.getEmployee().getId(),
            leave.getEmployee().getFirstName(),
            leave.getEmployee().getMiddleName(),
            leave.getEmployee().getLastName(),
            leave.getEmployee().getEmail()
        )
    );
  }

  public Leave toLeave(LeaveInsertRequest leaveInsertRequest,
      LocalDate requestDate,
      LeaveState leaveState,
      Employee employee) {
    Objects.requireNonNull(leaveInsertRequest);
    Objects.requireNonNull(requestDate);
    Objects.requireNonNull(leaveState);
    Objects.requireNonNull(employee);

    return new Leave(
        requestDate,
        leaveInsertRequest.startDate(),
        leaveInsertRequest.endDate(),
        leaveState,
        leaveInsertRequest.type(),
        employee
    );
  }

  public Leave toLeave(LeaveUpdateRequest leaveUpdateRequest, Leave leave) {
    Objects.requireNonNull(leaveUpdateRequest);
    Objects.requireNonNull(leave);

    leave.setStartDate(leaveUpdateRequest.startDate());
    leave.setEndDate(leaveUpdateRequest.endDate());
    leave.setType(leaveUpdateRequest.type());

    return leave;
  }
}
