package com.pavlin.employeemanagement.repository;

import com.pavlin.employeemanagement.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

  @Override
  @EntityGraph(value = "Employee.team")
  Optional<Employee> findById(UUID id);

  @Override
  @EntityGraph(value = "Employee.team")
  List<Employee> findAll();

  @EntityGraph(value = "Employee.rolesAndPrivileges")
  @Query("SELECT e FROM Employee e "
      + "WHERE e.username = :usernameOrEmail OR e.email = :usernameOrEmail")
  Optional<Employee> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
