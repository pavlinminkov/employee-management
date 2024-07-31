package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.dto.TeamResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface TeamService {

  TeamResponse getTeamById(UUID teamId);

  List<TeamResponse> getAllTeams();

  @Transactional
  UUID createTeam(TeamRequest teamRequest);

  @Transactional
  void updateTeam(UUID teamId, TeamRequest teamRequest);

  @Transactional
  void deleteTeam(UUID teamId);
}
