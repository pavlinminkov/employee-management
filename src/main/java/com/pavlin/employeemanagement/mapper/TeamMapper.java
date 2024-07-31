package com.pavlin.employeemanagement.mapper;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.dto.TeamResponse;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Team;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

  public TeamResponse toTeamResponse(Team team) {
    UUID leadId = Objects.nonNull(team.getLead()) ? team.getLead().getId() : null;

    return new TeamResponse(team.getId(), team.getName(), leadId);
  }

  public Team toTeam(TeamRequest teamRequest) {
    if (teamRequest.getLeadId() != null) {
      Employee employee = new Employee();
      employee.setId(teamRequest.getLeadId());

      return new Team(teamRequest.getName(), employee);
    }

    return new Team(teamRequest.getName());
  }
}
