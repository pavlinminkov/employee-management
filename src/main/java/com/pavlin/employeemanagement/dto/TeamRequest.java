package com.pavlin.employeemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Team}
 */
public class TeamRequest implements Serializable {

  @NotNull
  @Size(max = 255)
  private final String name;
  private UUID leadId;

  public TeamRequest(String name) {
    this.name = name;
  }

  public TeamRequest(String name, UUID leadId) {
    this.name = name;
    this.leadId = leadId;
  }

  public String getName() {
    return name;
  }

  public UUID getLeadId() {
    return leadId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TeamRequest entity = (TeamRequest) o;
    return Objects.equals(this.name, entity.name)
        && Objects.equals(this.leadId, entity.leadId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, leadId);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "("
        + "name = " + name + ", "
        + "leadId = " + leadId + ")";
  }
}