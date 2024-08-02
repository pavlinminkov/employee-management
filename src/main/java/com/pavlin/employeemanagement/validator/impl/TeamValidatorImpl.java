package com.pavlin.employeemanagement.validator.impl;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.exception.common.DuplicateEntryException;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.TeamRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.TeamValidator;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TeamValidatorImpl implements TeamValidator {

  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageUtil messageUtil;

  public TeamValidatorImpl(TeamRepository teamRepository, EmployeeRepository employeeRepository,
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

    // TODO Rewrite with Objects.equals
    if (Objects.nonNull(request.leadId()) && isEntityLeadDifferent(request, entity)) {
      checkIfDuplicateLead(request);
    }

    checkIfEmployeeExists(request.leadId());
  }

  private void checkIfEmployeeExists(UUID leadId) {
    if (Objects.nonNull(leadId) && !employeeRepository.existsById(leadId)) {
      throw new NotFoundException(messageUtil.getMessage("employee.notfound", leadId));
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

  private boolean isEntityLeadDifferent(TeamRequest request, Team entity) {
    return Objects.isNull(entity.getLead()) || !request.leadId()
        .equals(entity.getLead().getId());
  }
}