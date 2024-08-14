package com.pavlin.employeemanagement.validator.impl;

import com.pavlin.employeemanagement.dto.EmployeeInsertRequest;
import com.pavlin.employeemanagement.dto.EmployeeUpdateRequest;
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
  public void validateCreation(EmployeeInsertRequest request) {
    checkIfTeamExists(request.teamId());
    checkIfDuplicateUsername(request.username());
    checkIfDuplicateEmail(request.email());
  }

  @Override
  public void validateUpdate(EmployeeUpdateRequest request, Employee entity) {
    if (!Objects.equals(request.username(), entity.getUsername())) {
      checkIfDuplicateUsername(request.username());
    }
    if (!Objects.equals(request.email(), entity.getEmail())) {
      checkIfDuplicateEmail(request.email());
    }
    checkIfTeamExists(request.teamId());
  }

  @Override
  public void validateDeletion(UUID id) {
    checkIfEmployeeExists(id);
  }

  private void checkIfEmployeeExists(UUID id) {
    if (!employeeRepository.existsById(id)) {
      throw new NotFoundException(messageUtil.getMessage("employee.not_found", id));
    }
  }

  private void checkIfTeamExists(UUID teamId) {
    if (!teamRepository.existsById(teamId)) {
      throw new NotFoundException(messageUtil.getMessage("team.not_found", teamId));
    }
  }

  private void checkIfDuplicateUsername(String username) {
    if (employeeRepository.existsByUsername(username)) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("employee.duplicate.username", username)
      );
    }
  }

  private void checkIfDuplicateEmail(String email) {
    if (employeeRepository.existsByEmail(email)) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("employee.duplicate.email", email)
      );
    }
  }
}
