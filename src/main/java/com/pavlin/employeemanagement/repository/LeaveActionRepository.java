package com.pavlin.employeemanagement.repository;

import com.pavlin.employeemanagement.model.LeaveAction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveActionRepository extends JpaRepository<LeaveAction, UUID> {

  @Override
  @EntityGraph(value = "LeaveAction.employee")
  Optional<LeaveAction> findById(UUID id);

  @Override
  @EntityGraph(value = "LeaveAction.employee")
  List<LeaveAction> findAll();

  @EntityGraph(value = "LeaveAction.employee")
  List<LeaveAction> findAllByLeave_Id(UUID id);
}
