package com.pavlin.employeemanagement.repository;

import com.pavlin.employeemanagement.model.Team;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, UUID> {

  boolean existsByName(String name);

  boolean existsByLead_Id(UUID id);

  Optional<Team> findByLead_Id(UUID id);

  @Query("select count(e) > 0 from Employee e where e.team = :team")
  boolean hasEmployees(@Param("team") Team team);
}
