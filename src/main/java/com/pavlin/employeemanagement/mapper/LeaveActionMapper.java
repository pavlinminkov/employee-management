package com.pavlin.employeemanagement.mapper;

import com.pavlin.employeemanagement.dto.LeaveActionInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveActionResponse;
import com.pavlin.employeemanagement.dto.LeaveActionResponse.EmployeeDetails;
import com.pavlin.employeemanagement.dto.LeaveActionUpdateRequest;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Leave;
import com.pavlin.employeemanagement.model.LeaveAction;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LeaveActionMapper {

  public LeaveActionResponse toLeaveActionResponse(LeaveAction leaveAction) {
    Objects.requireNonNull(leaveAction);

    Employee employee = leaveAction.getEmployee();
    EmployeeDetails employeeDetails = new EmployeeDetails(
        employee.getId(),
        employee.getFirstName(),
        employee.getMiddleName(),
        employee.getLastName(),
        employee.getEmail()
    );

    return new LeaveActionResponse(
        leaveAction.getId(),
        leaveAction.getDate(),
        leaveAction.getLeave().getId(),
        employeeDetails
    );
  }

  public LeaveAction toLeaveAction(LeaveActionInsertRequest leaveActionInsertRequest,
      LocalDate requestDate,
      Leave leave,
      Employee employee) {
    Objects.requireNonNull(leaveActionInsertRequest);
    Objects.requireNonNull(requestDate);
    Objects.requireNonNull(employee);
    Objects.requireNonNull(leave);

    return new LeaveAction(
        leaveActionInsertRequest.type(),
        requestDate,
        leave,
        employee
    );
  }

  public LeaveAction toLeaveAction(LeaveActionUpdateRequest leaveActionUpdateRequest,
      LeaveAction leave) {
    Objects.requireNonNull(leaveActionUpdateRequest);
    Objects.requireNonNull(leave);

    leave.setType(leaveActionUpdateRequest.type());

    return leave;
  }
}
