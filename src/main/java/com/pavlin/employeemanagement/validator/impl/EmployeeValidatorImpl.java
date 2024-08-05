package com.pavlin.employeemanagement.validator.impl;

import com.pavlin.employeemanagement.dto.EmployeeRequest;
import com.pavlin.employeemanagement.exception.common.DuplicateEntryException;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.TeamRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.EmployeeValidator;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorImpl implements EmployeeValidator {

  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageUtil messageUtil;

  public EmployeeValidatorImpl(TeamRepository teamRepository, EmployeeRepository employeeRepository,
      MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
    this.messageUtil = messageUtil;
  }

  @Override
  public void validateCreation(EmployeeRequest request) {
    checkIfTeamExists(request.teamId());
    checkIfDuplicateUsername(request);
    checkIfDuplicateEmail(request);
  }

  @Override
  public void validateUpdate(EmployeeRequest request, Employee entity) {
    if (!Objects.equals(request.username(), entity.getUsername())) {
      checkIfDuplicateUsername(request);
    }
    if (!Objects.equals(request.email(), entity.getEmail())) {
      checkIfDuplicateEmail(request);
    }
    checkIfTeamExists(request.teamId());
  }

  private void checkIfTeamExists(UUID teamId) {
    if (!teamRepository.existsById(teamId)) {
      throw new NotFoundException(messageUtil.getMessage("team.not_found", teamId));
    }
  }

  private void checkIfDuplicateUsername(EmployeeRequest employeeRequest) {
    if (employeeRepository.existsByUsername(employeeRequest.username())) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("employee.duplicate.username", employeeRequest.username())
      );
    }
  }

  private void checkIfDuplicateEmail(EmployeeRequest employeeRequest) {
    if (employeeRepository.existsByEmail(employeeRequest.email())) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("employee.duplicate.email", employeeRequest.email())
      );
    }
  }
}
