package com.pavlin.employeemanagement.validator.service.impl;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.exception.common.DuplicateEntryException;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.exception.common.RelatedEntityNotFoundException;
import com.pavlin.employeemanagement.exception.common.TeamNotEmptyException;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.TeamRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.service.TeamValidator;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TeamValidatorImpl implements TeamValidator {

  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageUtil messageUtil;

  public TeamValidatorImpl(TeamRepository teamRepository,
      EmployeeRepository employeeRepository,
      MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
    this.messageUtil = messageUtil;
  }

  @Override
  public void validateCreation(TeamRequest request) {
    checkIfDuplicateTeamName(request);
    if (Objects.nonNull(request.leadId())) {
      checkIfDuplicateLead(request);
    }
    checkIfEmployeeExists(request.leadId());
  }

  @Override
  public void validateUpdate(TeamRequest request, Team entity) {
    if (!Objects.equals(entity.getName(), request.name())) {
      checkIfDuplicateTeamName(request);
    }

    if (Objects.nonNull(request.leadId()) && isEntityLeadDifferent(request, entity)) {
      checkIfDuplicateLead(request);
    }

    checkIfEmployeeExists(request.leadId());
  }

  @Override
  public void validateDeletion(UUID id) {
    checkIfTeamExists(id);
    checkIfTeamIsEmpty(id);
  }

  private void checkIfTeamExists(UUID leadId) {
    if (!teamRepository.existsById(leadId)) {
      throw new NotFoundException(messageUtil.getMessage("team.not_found", leadId));
    }
  }

  private void checkIfEmployeeExists(UUID leadId) {
    if (Objects.nonNull(leadId) && !employeeRepository.existsById(leadId)) {
      throw new RelatedEntityNotFoundException(
          messageUtil.getMessage("employee.not_found", leadId));
    }
  }

  private void checkIfDuplicateLead(TeamRequest teamRequest) {
    UUID leadId = teamRequest.leadId();

    if (teamRepository.existsByLead_Id(leadId)) {
      throw new DuplicateEntryException(messageUtil.getMessage("team.duplicate.lead", leadId));
    }
  }

  private void checkIfDuplicateTeamName(TeamRequest teamRequest) {
    if (teamRepository.existsByName(teamRequest.name())) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("team.duplicate.name", teamRequest.name()));
    }
  }

  private void checkIfTeamIsEmpty(UUID id) {
    if (teamRepository.hasEmployees(id)) {
      throw new TeamNotEmptyException(messageUtil.getMessage("team.not_empty", id));
    }
  }

  private boolean isEntityLeadDifferent(TeamRequest request, Team entity) {
    if (Objects.isNull(entity.getLead())) {
      return true;
    }

    return !request.leadId().equals(entity.getLead().getId());
  }
}