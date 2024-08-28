package com.pavlin.employeemanagement.service.impl;

import com.pavlin.employeemanagement.dto.LeaveActionInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveActionResponse;
import com.pavlin.employeemanagement.dto.LeaveActionUpdateRequest;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.mapper.LeaveActionMapper;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.model.Leave;
import com.pavlin.employeemanagement.model.LeaveAction;
import com.pavlin.employeemanagement.model.common.LeaveActionType;
import com.pavlin.employeemanagement.model.common.LeaveState;
import com.pavlin.employeemanagement.repository.EmployeeRepository;
import com.pavlin.employeemanagement.repository.LeaveActionRepository;
import com.pavlin.employeemanagement.repository.LeaveRepository;
import com.pavlin.employeemanagement.service.LeaveActionService;
import com.pavlin.employeemanagement.util.MessageUtil;
import com.pavlin.employeemanagement.validator.service.LeaveActionValidator;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LeaveActionServiceImpl implements LeaveActionService {

  private final LeaveActionRepository leaveActionRepository;
  private final LeaveActionMapper leaveActionMapper;
  private final LeaveActionValidator leaveActionValidator;
  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(LeaveActionServiceImpl.class);
  private final EmployeeRepository employeeRepository;
  private final LeaveRepository leaveRepository;

  public LeaveActionServiceImpl(LeaveActionRepository leaveActionRepository,
      LeaveActionMapper leaveActionMapper, LeaveActionValidator leaveActionValidator,
      MessageUtil messageUtil, EmployeeRepository employeeRepository,
      LeaveRepository leaveRepository) {
    this.leaveActionRepository = leaveActionRepository;
    this.leaveActionMapper = leaveActionMapper;
    this.leaveActionValidator = leaveActionValidator;
    this.messageUtil = messageUtil;
    this.employeeRepository = employeeRepository;
    this.leaveRepository = leaveRepository;
  }

  @Override
  public LeaveActionResponse getLeaveActionById(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Fetching leave action by id: {}", id);

    return leaveActionMapper.toLeaveActionResponse(retrieveLeaveActionById(id));
  }

  // May be admin only
  @Override
  public List<LeaveActionResponse> getAllLeaveActions() {
    logger.debug("Fetching all leave actions");

    return leaveActionRepository.findAll().stream()
        .map(leaveActionMapper::toLeaveActionResponse)
        .toList();
  }

  @Override
  public List<LeaveActionResponse> getAllLeaveActionsByLeaveId(UUID leaveId) {
    Objects.requireNonNull(leaveId);
    logger.debug("Fetching all leave actions by leave id: {}", leaveId);

    return leaveActionRepository.findAllByLeave_Id(leaveId).stream()
        .map(leaveActionMapper::toLeaveActionResponse)
        .toList();
  }

  @Override
  @Transactional
  public UUID createLeaveAction(LeaveActionInsertRequest leaveActionInsertRequest) {
    Objects.requireNonNull(leaveActionInsertRequest);
    logger.debug("Creating leave action");

    leaveActionValidator.validateCreation(leaveActionInsertRequest);
    Employee employee = employeeRepository.getReferenceById(leaveActionInsertRequest.employeeId());
    Leave leave = leaveRepository.getReferenceById(leaveActionInsertRequest.leaveId());

    LeaveAction leaveAction = leaveActionMapper.toLeaveAction(leaveActionInsertRequest,
        LocalDate.now(), leave, employee);

    if (leaveAction.getType().equals(LeaveActionType.APPROVE)) {
      leave.setState(LeaveState.APPROVED);
    }
    if (leaveAction.getType().equals(LeaveActionType.DENY)) {
      leave.setState(LeaveState.DENIED);
    }

    leaveRepository.save(leave);
    return leaveActionRepository.save(leaveAction).getId();
  }

  @Override
  @Transactional
  public void updateLeaveAction(UUID id, LeaveActionUpdateRequest leaveActionUpdateRequest) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(leaveActionUpdateRequest);
    logger.debug("Updating leave action with id: {}", id);

    LeaveAction leaveAction = retrieveLeaveActionById(id);
    leaveActionValidator.validateUpdate(leaveActionUpdateRequest, leaveAction);
    Leave leave = leaveRepository.getReferenceById(leaveAction.getLeave().getId());

    if (leaveActionUpdateRequest.type().equals(LeaveActionType.APPROVE)) {
      leave.setState(LeaveState.APPROVED);
    }
    if (leaveActionUpdateRequest.type().equals(LeaveActionType.DENY)) {
      leave.setState(LeaveState.DENIED);
    }

    leaveRepository.save(leave);
    leaveActionRepository.save(
        leaveActionMapper.toLeaveAction(leaveActionUpdateRequest, leaveAction));
  }

  @Override
  @Transactional
  public void deleteLeaveAction(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Deleting leave action with id: {}", id);

    leaveActionValidator.validateDeletion(id);
    LeaveAction leaveAction = retrieveLeaveActionById(id);

    Leave leave = leaveAction.getLeave();
    leave.setState(LeaveState.PENDING);

    leaveRepository.save(leave);
    leaveActionRepository.deleteById(id);
  }

  private LeaveAction retrieveLeaveActionById(UUID id) {
    return leaveActionRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("leave_action.not_found", id)
        ));
  }
}
