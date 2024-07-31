package com.pavlin.employeemanagement.service.impl;

import com.pavlin.employeemanagement.dto.EmployeeRequest;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.mapper.EmployeeMapper;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.service.EmployeeService;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.EmployeeValidator;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final EmployeeValidator employeeValidator;
  private final MessageUtil messageUtil;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

  public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
      EmployeeValidator employeeValidator, MessageUtil messageUtil,
      PasswordEncoder passwordEncoder) {
    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
    this.employeeValidator = employeeValidator;
    this.messageUtil = messageUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public EmployeeResponse getEmployeeById(UUID id) {
    logger.debug("Fetching employee with id: {}", id);

    Employee employee = retrieveEmployeeById(id);

    return employeeMapper.toEmployeeResponse(employee);
  }

  @Override
  public List<EmployeeResponse> getAllEmployees() {
    logger.debug("Fetching all employees");

    return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponse).toList();
  }

  @Override
  @Transactional
  public UUID createEmployee(EmployeeRequest employeeRequest) {
    logger.debug("Creating a new employee");

    employeeValidator.validateCreation(employeeRequest);

    Employee employee = employeeMapper.toEmployee(employeeRequest);
    employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
    employee.setIsEnabled(true);

    return employeeRepository.save(employee).getId();
  }

  @Override
  @Transactional
  public void updateEmployee(UUID id, EmployeeRequest employeeRequest) {
    logger.debug("Updating employee with id: {}", id);

    Employee employee = retrieveEmployeeById(id);
    employeeValidator.validateUpdate(employeeRequest, employee);

    employee = employeeMapper.toEmployee(employeeRequest, employee);
    employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));

    employeeRepository.save(employee);
  }

  @Override
  @Transactional
  public void deleteEmployee(UUID id) {
    logger.debug("Deleting employee with id: {}", id);

    Employee employee = retrieveEmployeeById(id);

    employeeRepository.delete(employee);
  }

  private Employee retrieveEmployeeById(UUID id) {
    return employeeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("employee.notfound", id)
        ));
  }
}
