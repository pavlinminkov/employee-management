package com.pavlin.employeemanagement.service.impl;

import com.pavlin.employeemanagement.mapper.UserMapper;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final EmployeeRepository employeeRepository;
  private final UserMapper userMapper;
  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(EmployeeRepository employeeRepository, UserMapper userMapper,
      MessageUtil messageUtil) {
    this.employeeRepository = employeeRepository;
    this.userMapper = userMapper;
    this.messageUtil = messageUtil;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    logger.debug("Loading user by username/email: {}", username);

    Employee employee = employeeRepository.findByUsernameOrEmail(username).orElseThrow(
        () -> new UsernameNotFoundException(messageUtil.getMessage("user.not_found", username))
    );

    return userMapper.toUserDetails(employee);
  }
}
