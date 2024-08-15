package com.pavlin.employeemanagement.repository;

import com.pavlin.employeemanagement.model.Leave;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {

  @Query("select l from Leave l where l.id = ?1")
  @EntityGraph(value = "Leave.employee")
  Optional<Leave> findByIdWithEmployee(UUID id);

  @Query("select l from Leave l")
  @EntityGraph(value = "Leave.employee")
  Page<Leave> findAllWithEmployee(Pageable pageable);

  @EntityGraph(value = "Leave.employee")
  Page<Leave> findAllByEmployeeId(UUID employeeId, Pageable pageable);

  @EntityGraph(value = "Leave.employee")
  Page<Leave> findAllByEmployee_Team_Lead_Id(UUID id, Pageable pageable);


}
