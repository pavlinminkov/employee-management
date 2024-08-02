package com.pavlin.employeemanagement.mapper;

import com.pavlin.employeemanagement.dto.EmployeeRequest;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
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

  public Employee toEmployee(EmployeeRequest employeeRequest) {
    Team team = new Team();
    team.setId(employeeRequest.teamId());

    return new Employee(
        employeeRequest.firstName(),
        employeeRequest.middleName(),
        employeeRequest.lastName(),
        employeeRequest.username(),
        employeeRequest.password(),
        employeeRequest.email(),
        team
    );
  }

  public Employee toEmployee(EmployeeRequest employeeRequest, Employee employee) {
    Team team = new Team();
    team.setId(employeeRequest.teamId());

    employee.setFirstName(employeeRequest.firstName());
    employee.setMiddleName(employeeRequest.middleName());
    employee.setLastName(employeeRequest.lastName());
    employee.setUsername(employeeRequest.username());
    employee.setPassword(employeeRequest.password());
    employee.setEmail(employeeRequest.email());
    employee.setTeam(team);

    return employee;
  }
}
