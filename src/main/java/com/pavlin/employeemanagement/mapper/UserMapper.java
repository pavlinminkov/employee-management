package com.pavlin.employeemanagement.mapper;

import com.pavlin.employeemanagement.model.Employee;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDetails toUserDetails(Employee employee) {
    Objects.requireNonNull(employee);

    return new User(
        employee.getUsername(),
        employee.getPassword(),
        extractGrantedAuthorities(employee)
    );
  }

  private Collection<GrantedAuthority> extractGrantedAuthorities(Employee employee) {
    return employee.getRoles().stream()
        .flatMap(role -> role.getPrivileges().stream())
        .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
        .collect(Collectors.toUnmodifiableList());
  }
}
