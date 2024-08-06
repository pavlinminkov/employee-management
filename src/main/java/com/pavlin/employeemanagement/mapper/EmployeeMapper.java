package com.pavlin.employeemanagement.mapper;

import com.pavlin.employeemanagement.dto.EmployeeInsertRequest;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
import com.pavlin.employeemanagement.dto.EmployeeUpdateRequest;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Team;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

  private final TeamMapper teamMapper;

  public EmployeeMapper(TeamMapper teamMapper) {
    this.teamMapper = teamMapper;
  }

  public EmployeeResponse toEmployeeResponse(Employee employee) {
    return new EmployeeResponse(
        employee.getId(),
        employee.getFirstName(),
        employee.getMiddleName(),
        employee.getLastName(),
        employee.getEmail(),
        teamMapper.toTeamResponse(employee.getTeam())
    );
  }

  public Employee toEmployee(EmployeeInsertRequest employeeInsertRequest) {
    Team team = new Team();
    team.setId(employeeInsertRequest.teamId());

    return new Employee(
        employeeInsertRequest.firstName(),
        employeeInsertRequest.middleName(),
        employeeInsertRequest.lastName(),
        employeeInsertRequest.username(),
        employeeInsertRequest.password(),
        employeeInsertRequest.email(),
        team
    );
  }

  public Employee toEmployee(EmployeeUpdateRequest employeeUpdateRequest, Employee employee) {
    Team team = new Team();
    team.setId(employeeUpdateRequest.teamId());

    employee.setFirstName(employeeUpdateRequest.firstName());
    employee.setMiddleName(employeeUpdateRequest.middleName());
    employee.setLastName(employeeUpdateRequest.lastName());
    employee.setUsername(employeeUpdateRequest.username());
    employee.setEmail(employeeUpdateRequest.email());
    employee.setTeam(team);

    return employee;
  }
}
