package com.pavlin.employeemanagement.service.impl;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.dto.TeamResponse;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.mapper.TeamMapper;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.repository.TeamRepository;
import com.pavlin.employeemanagement.service.TeamService;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.TeamValidator;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl implements TeamService {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;
  private final TeamValidator teamValidator;
  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

  public TeamServiceImpl(TeamRepository teamRepository,
      TeamMapper teamMapper, TeamValidator teamValidator, MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.teamMapper = teamMapper;
    this.teamValidator = teamValidator;
    this.messageUtil = messageUtil;
  }

  @Override
  public TeamResponse getTeamById(UUID id) {
    logger.debug("Fetching team with id: {}", id);

    Team team = retrieveTeamById(id);

    return teamMapper.toTeamResponse(team);
  }

  @Override
  public List<TeamResponse> getAllTeams() {
    logger.debug("Fetching all teams");

    return teamRepository.findAll().stream().map(teamMapper::toTeamResponse).toList();
  }

  @Override
  @Transactional
  public UUID createTeam(TeamRequest teamRequest) {
    logger.debug("Creating a new team");

    teamValidator.validateCreation(teamRequest);

    Team team = teamMapper.toTeam(teamRequest);
    return teamRepository.save(team).getId();
  }

  @Override
  @Transactional
  public void updateTeam(UUID id, TeamRequest teamRequest) {
    logger.debug("Updating team with id: {}", id);

    Team team = retrieveTeamById(id);
    teamValidator.validateUpdate(teamRequest, team);

    teamRepository.save(teamMapper.toTeam(teamRequest, team));
  }

  @Override
  @Transactional
  public void deleteTeam(UUID id) {
    logger.debug("Deleting team with id: {}", id);

    Team team = retrieveTeamById(id);
    teamValidator.validateDeletion(team);

    teamRepository.delete(retrieveTeamById(id));
  }

  private Team retrieveTeamById(UUID id) {
    return teamRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("team.not_found", id)
        ));
  }
}
