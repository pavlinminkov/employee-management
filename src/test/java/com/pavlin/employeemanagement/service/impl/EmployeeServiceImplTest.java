package com.pavlin.employeemanagement.service.impl;

import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEES;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_EMAIL;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_FIRST_NAME;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_ID;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_LAST_NAME;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_MIDDLE_NAME;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_PASSWORD;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_UNHASHED_PASSWORD;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_USERNAME;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_2;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_3;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_4;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_ID;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_PASSWORD;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_5;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_INSERT_REQUEST;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSES;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_1;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_2;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_3;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_4;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_5;
import static com.pavlin.employeemanagement.util.EmployeeMocks.EMPLOYEE_UPDATE_REQUEST;
import static com.pavlin.employeemanagement.util.EmployeeMocks.TEAM_1;
import static com.pavlin.employeemanagement.util.EmployeeMocks.TEAM_1_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pavlin.employeemanagement.config.MessageSourceConfig;
import com.pavlin.employeemanagement.dto.EmployeeResponse;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.mapper.EmployeeMapper;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.TeamRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.service.common.EmployeeValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

  @Mock
  EmployeeRepository employeeRepository;

  @Mock
  EmployeeMapper employeeMapper;

  @Mock
  EmployeeValidator employeeValidator;

  @Mock
  TeamRepository teamRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  @Spy
  MessageUtil messageUtil = new MessageUtil(new MessageSourceConfig().messageSource());

  @InjectMocks
  EmployeeServiceImpl employeeService;

  @Test
  void givenValidEmployeeId_whenGetEmployeeById_thenReturnEmployeeResponse() {
    when(employeeRepository.findById(EMPLOYEE_1_ID)).thenReturn(Optional.of(EMPLOYEE_1));
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_1)).thenReturn(EMPLOYEE_RESPONSE_1);

    EmployeeResponse result = employeeService.getEmployeeById(EMPLOYEE_1_ID);

    assertEquals(EMPLOYEE_RESPONSE_1, result);
  }

  @Test
  void givenInvalidEmployeeId_whenGetEmployeeById_thenThrowNotFoundException() {
    UUID invalidEmployeeId = UUID.fromString("3b9e314c-3f8e-41ab-84c2-6d327a81aa84");
    String expectedMessage = messageUtil.getMessage("employee.not_found", invalidEmployeeId);
    when(employeeRepository.findById(invalidEmployeeId)).thenReturn(Optional.empty());

    NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
      employeeService.getEmployeeById(invalidEmployeeId);
    });

    assertEquals(expectedMessage, notFoundException.getMessage());
  }

  @Test
  void givenEmployees_whenGetAllEmployees_thenReturnEmployeeResponseList() {
    when(employeeRepository.findAll()).thenReturn(EMPLOYEES);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_1)).thenReturn(EMPLOYEE_RESPONSE_1);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_2)).thenReturn(EMPLOYEE_RESPONSE_2);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_3)).thenReturn(EMPLOYEE_RESPONSE_3);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_4)).thenReturn(EMPLOYEE_RESPONSE_4);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_5)).thenReturn(EMPLOYEE_RESPONSE_5);

    List<EmployeeResponse> result = employeeService.getAllEmployees();

    assertEquals(EMPLOYEE_RESPONSES, result);
  }

  @Test
  void givenEmptyRepository_whenGetAllEmployees_thenReturnEmptyList() {
    when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

    List<EmployeeResponse> result = employeeService.getAllEmployees();

    assertEquals(new ArrayList<>(), result);
  }

  @Test
  void givenAnyEmployeeInsertRequest_whenCreateEmployee_thenValidateEmployeeInsertRequest() {
    when(employeeMapper.toEmployee(EMPLOYEE_INSERT_REQUEST)).thenReturn(EMPLOYEE_1);
    when(employeeRepository.save(EMPLOYEE_1)).thenReturn(EMPLOYEE_1);

    employeeService.createEmployee(EMPLOYEE_INSERT_REQUEST);

    verify(employeeValidator, times(1)).validateCreation(EMPLOYEE_INSERT_REQUEST);
  }

  @Test
  void givenValidEmployeeInsertRequest_whenCreateEmployee_thenSaveEmployeeAndReturnsUUID() {
    Employee employee = new Employee(
        EMPLOYEE_1.getFirstName(),
        EMPLOYEE_1.getMiddleName(),
        EMPLOYEE_1.getLastName(),
        EMPLOYEE_1.getUsername(),
        EMPLOYEE_1_UNHASHED_PASSWORD,
        EMPLOYEE_1.getEmail(),
        EMPLOYEE_1.getTeam()
    );

    when(employeeMapper.toEmployee(EMPLOYEE_INSERT_REQUEST)).thenReturn(employee);
    when(passwordEncoder.encode(EMPLOYEE_1_UNHASHED_PASSWORD)).thenReturn(EMPLOYEE_1_PASSWORD);
    when(employeeRepository.save(employee)).thenReturn(EMPLOYEE_1);

    UUID result = employeeService.createEmployee(EMPLOYEE_INSERT_REQUEST);

    assertTrue(employee.getIsEnabled());
    assertEquals(EMPLOYEE_1_PASSWORD, employee.getPassword());
    verify(employeeRepository, times(1)).save(employee);
    assertEquals(EMPLOYEE_1_ID, result);
  }

  @Test
  void givenAnyEmployeeUpdateRequestAndId_whenUpdateEmployee_thenValidateEmployeeUpdateRequest() {
    when(employeeRepository.findById(EMPLOYEE_1_ID)).thenReturn(Optional.of(EMPLOYEE_1));
    when(employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_1)).thenReturn(EMPLOYEE_1);
    when(employeeRepository.save(EMPLOYEE_1)).thenReturn(EMPLOYEE_1);

    employeeService.updateEmployee(EMPLOYEE_1_ID, EMPLOYEE_UPDATE_REQUEST);

    verify(employeeValidator, times(1))
        .validateUpdate(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_1);
  }

  @Test
  void givenInvalidEmployeeId_whenUpdateEmployee_thenThrowNotFoundException() {
    UUID invalidEmployeeId = UUID.fromString("3b9e314c-3f8e-41ab-84c2-6d327a81aa84");
    String expectedMessage = messageUtil.getMessage("employee.not_found", invalidEmployeeId);
    when(employeeRepository.findById(invalidEmployeeId)).thenReturn(Optional.empty());

    NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
      employeeService.updateEmployee(invalidEmployeeId, EMPLOYEE_UPDATE_REQUEST);
    });

    assertEquals(expectedMessage, notFoundException.getMessage());
  }

  @Test
  void givenValidEmployeeUpdateRequestAndId_whenUpdateEmployee_thenUpdateEmployee() {
    Employee employee = new Employee(
        EMPLOYEE_1_FIRST_NAME,
        EMPLOYEE_1_MIDDLE_NAME,
        EMPLOYEE_1_LAST_NAME,
        EMPLOYEE_1_USERNAME,
        EMPLOYEE_4_PASSWORD,
        EMPLOYEE_1_EMAIL,
        TEAM_1
    );

    when(employeeRepository.findById(EMPLOYEE_4_ID)).thenReturn(Optional.of(EMPLOYEE_4));
    when(employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_4)).thenReturn(employee);
    when(employeeRepository.save(employee)).thenReturn(employee);

    employeeService.updateEmployee(EMPLOYEE_4_ID, EMPLOYEE_UPDATE_REQUEST);

    verify(employeeRepository, times(1)).save(employee);
  }

  @Test
  void givenAnyEmployeeId_whenDeleteEmployee_thenValidateEmployeeDeletion() {
    employeeService.deleteEmployee(EMPLOYEE_1_ID);

    verify(employeeValidator, times(1)).validateDeletion(EMPLOYEE_1_ID);
  }

  @Test
  void givenValidTeamLeadId_whenDeleteEmployee_thenDeleteEmployeeAndUnsetAsTheTeamLead() {
    Team team = new Team(TEAM_1_NAME, EMPLOYEE_1);

    when(teamRepository.findByLead_Id(EMPLOYEE_1_ID)).thenReturn(Optional.of(team));

    employeeService.deleteEmployee(EMPLOYEE_1_ID);

    verify(teamRepository, times(1)).findByLead_Id(EMPLOYEE_1_ID);
    verify(employeeRepository, times(1)).deleteById(EMPLOYEE_1_ID);
    assertNull(team.getLead());
  }

}