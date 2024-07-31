package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.EmployeeRequest;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

  EmployeeResponse getEmployeeById(UUID id);

  List<EmployeeResponse> getAllEmployees();

  UUID createEmployee(EmployeeRequest employeeRequest);

  void updateEmployee(UUID id, EmployeeRequest employeeRequest);

  void deleteEmployee(UUID id);
}
