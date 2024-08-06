package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.EmployeeInsertRequest;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
import com.pavlin.employeemanagement.dto.EmployeeUpdateRequest;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

  EmployeeResponse getEmployeeById(UUID id);

  List<EmployeeResponse> getAllEmployees();

  UUID createEmployee(EmployeeInsertRequest employeeInsertRequest);

  void updateEmployee(UUID id, EmployeeUpdateRequest employeeUpdateRequest);

  void deleteEmployee(UUID id);
}
