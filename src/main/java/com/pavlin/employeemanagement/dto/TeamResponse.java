package com.pavlin.employeemanagement.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO for {@link com.pavlin.employeemanagement.model.Team}
 */
public class TeamResponse implements Serializable {

  private final UUID id;
  private final String name;
  private final UUID leadId;

  public TeamResponse(UUID id, String name, UUID leadId) {
    this.id = id;
    this.name = name;
    this.leadId = leadId;
  }

  public UUID getId() {
    return id;
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
    TeamResponse entity = (TeamResponse) o;
    return Objects.equals(this.id, entity.id)
        && Objects.equals(this.name, entity.name)
        && Objects.equals(this.leadId, entity.leadId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, leadId);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "("
        + "id = " + id + ", "
        + "name = " + name + ", "
        + "leadId = " + leadId + ")";
  }
}