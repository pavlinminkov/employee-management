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
    team.setId(employeeRequest.getTeamId());

    return new Employee(
        employeeRequest.getFirstName(),
        employeeRequest.getMiddleName(),
        employeeRequest.getLastName(),
        employeeRequest.getUsername(),
        employeeRequest.getPassword(),
        employeeRequest.getEmail(),
        team
    );
  }

  public Employee toEmployee(EmployeeRequest employeeRequest, Employee employee) {
    Team team = new Team();
    team.setId(employeeRequest.getTeamId());

    employee.setFirstName(employeeRequest.getFirstName());
    employee.setMiddleName(employeeRequest.getMiddleName());
    employee.setLastName(employeeRequest.getLastName());
    employee.setUsername(employeeRequest.getUsername());
    employee.setPassword(employeeRequest.getPassword());
    employee.setEmail(employeeRequest.getEmail());
    employee.setTeam(team);

    return employee;
  }
}
