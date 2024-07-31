package com.pavlin.employeemanagement.service;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.dto.TeamResponse;
import java.util.List;
import java.util.UUID;

public interface TeamService {

  TeamResponse getTeamById(UUID teamId);

  List<TeamResponse> getAllTeams();

  UUID createTeam(TeamRequest teamRequest);

  void updateTeam(UUID teamId, TeamRequest teamRequest);

  void deleteTeam(UUID teamId);
}
