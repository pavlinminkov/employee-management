package com.pavlin.employeemanagement.controller;

import com.pavlin.employeemanagement.dto.EmployeeInsertRequest;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
import com.pavlin.employeemanagement.dto.EmployeeUpdateRequest;
import com.pavlin.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable UUID id) {
    return ResponseEntity.ok(employeeService.getEmployeeById(id));
  }

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
    return ResponseEntity.ok(employeeService.getAllEmployees());
  }

  @PostMapping
  public ResponseEntity<Void> createEmployee(
      @Valid @RequestBody EmployeeInsertRequest employeeInsertRequest) {
    UUID employeeId = employeeService.createEmployee(employeeInsertRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/employees/{id}")
        .buildAndExpand(employeeId)
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateEmployee(
      @PathVariable UUID id,
      @Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest
  ) {
    employeeService.updateEmployee(id, employeeUpdateRequest);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
    employeeService.deleteEmployee(id);

    return ResponseEntity.noContent().build();
  }
}
