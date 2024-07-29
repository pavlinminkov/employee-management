package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.dto.TeamResponse;
import com.pavlin.employeemanagement.exception.common.DuplicateEntryException;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.mapper.TeamMapper;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.TeamRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;
  private final TeamMapper teamMapper;
  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(TeamService.class);

  public TeamService(TeamRepository teamRepository, EmployeeRepository employeeRepository,
      TeamMapper teamMapper, MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
    this.teamMapper = teamMapper;
    this.messageUtil = messageUtil;
  }

  public TeamResponse getTeamById(UUID teamId) {
    logger.debug("Fetching team with id: {}", teamId);

    Team team = retrieveTeamById(teamId);

    return teamMapper.toTeamResponse(team);
  }

  public List<TeamResponse> getAllTeams() {
    logger.debug("Fetching all teams");

    return teamRepository.findAll().stream().map(teamMapper::toTeamResponse).toList();
  }

  @Transactional
  public UUID createTeam(TeamRequest teamRequest) {
    logger.debug("Creating a new team");

    checkForDuplicateTeamName(teamRequest);

    Team team = teamMapper.toTeam(teamRequest);
    return teamRepository.save(team).getId();
  }

  @Transactional
  public void updateTeam(UUID teamId, TeamRequest teamRequest) {
    logger.debug("Updating team with id: {}", teamId);

    Team team = retrieveTeamById(teamId);

    if (!Objects.equals(team.getName(), teamRequest.getName())) {
      checkForDuplicateTeamName(teamRequest);
    }

    team.setName(teamRequest.getName());
    team.setLead(retrieveEmployeeById(teamRequest.getLeadId()));

    teamRepository.save(team);
  }

  @Transactional
  public void deleteTeam(UUID teamId) {
    logger.debug("Deleting team with id: {}", teamId);

    teamRepository.delete(retrieveTeamById(teamId));
  }

  private Team retrieveTeamById(UUID teamId) {
    return teamRepository
        .findById(teamId)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("team.notfound", teamId)
        ));
  }

  private Employee retrieveEmployeeById(UUID leadId) {
    if (Objects.isNull(leadId)) {
      return null;
    }
    return employeeRepository.findById(leadId)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("employee.notfound", leadId)
        ));
  }

  private void checkForDuplicateTeamName(TeamRequest teamRequest) {
    if (teamRepository.existsByName(teamRequest.getName())) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("team.duplicate.name", teamRequest.getName()));
    }
  }
}
