package com.pavlin.employeemanagement.validator.service.impl;

import com.pavlin.employeemanagement.dto.LeaveInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveUpdateRequest;
import com.pavlin.employeemanagement.exception.common.InvalidArgumentException;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.exception.common.OverlappingLeaveException;
import com.pavlin.employeemanagement.model.Leave;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.LeaveRepository;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.service.LeaveValidator;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LeaveValidatorImpl implements LeaveValidator {

  private final LeaveRepository leaveRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageUtil messageUtil;

  public LeaveValidatorImpl(EmployeeRepository employeeRepository, MessageUtil messageUtil,
      LeaveRepository leaveRepository) {
    this.employeeRepository = employeeRepository;
    this.messageUtil = messageUtil;
    this.leaveRepository = leaveRepository;
  }

  @Override
  public void validateCreation(LeaveInsertRequest request) {
    checkIfStartDateBeforeEndDate(request.startDate(), request.endDate());
    checkIfEmployeeExists(request.employeeId());
    checkIfDatesOverlap(request.startDate(), request.endDate(), request.employeeId());
  }

  @Override
  public void validateUpdate(LeaveUpdateRequest request, Leave entity) {
    checkIfStartDateBeforeEndDate(request.startDate(), request.endDate());
    checkIfLeaveIsInThePast(entity.getStartDate());
    checkIfDatesOverlap(request.startDate(), request.endDate(), entity.getEmployee().getId(),
        entity.getId());
  }

  @Override
  public void validateDeletion(UUID id) {
    checkIfLeaveExists(id);
  }

  private void checkIfLeaveExists(UUID id) {
    if (!leaveRepository.existsById(id)) {
      throw new NotFoundException(messageUtil.getMessage("leave.not_found", id));
    }
  }

  private void checkIfEmployeeExists(UUID id) {
    if (!employeeRepository.existsById(id)) {
      throw new NotFoundException(messageUtil.getMessage("employee.not_found", id));
    }
  }

  private void checkIfLeaveIsInThePast(LocalDate startDate) {
    if (startDate.isBefore(LocalDate.now())) {
      throw new InvalidArgumentException(
          messageUtil.getMessage("leave.past_leave_edit_not_allowed"));
    }
  }

  private void checkIfStartDateBeforeEndDate(LocalDate startDate, LocalDate endDate) {
    if (!startDate.isBefore(endDate)) {
      throw new InvalidArgumentException(
          messageUtil.getMessage("leave.start_date_before_end_date_not_allowed",
              startDate, endDate));
    }
  }

  private void checkIfDatesOverlap(LocalDate startDate, LocalDate endDate, UUID employeeId) {
    checkIfDatesOverlap(startDate, endDate, employeeId, null);
  }

  private void checkIfDatesOverlap(LocalDate startDate, LocalDate endDate, UUID employeeId,
      UUID leaveId) {
    List<Leave> overlappingLeaves = leaveRepository.findOverlappingLeaves(employeeId, leaveId,
        startDate, endDate);

    if (!overlappingLeaves.isEmpty()) {
      List<LocalDate[]> overlappingDates = overlappingLeaves.stream()
          .map(leave -> new LocalDate[]{leave.getStartDate(), leave.getEndDate()})
          .toList();

      throw new OverlappingLeaveException(overlappingDates);
    }
  }
}
