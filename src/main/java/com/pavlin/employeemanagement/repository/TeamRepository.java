package com.pavlin.employeemanagement.repository;

import com.pavlin.employeemanagement.model.Team;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, UUID> {

  boolean existsByName(String name);

  boolean existsByLead_Id(UUID id);

  Optional<Team> findByLead_Id(UUID id);
}
