package com.pavlin.employeemanagement.validator.service.impl;

import com.pavlin.employeemanagement.dto.LeaveActionInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveActionUpdateRequest;
import com.pavlin.employeemanagement.model.LeaveAction;
import com.pavlin.employeemanagement.validator.service.LeaveActionValidator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LeaveActionValidatorImpl implements LeaveActionValidator {

  @Override
  public void validateCreation(LeaveActionInsertRequest request) {
    // TODO Employee needs to be lead to create it
  }

  @Override
  public void validateUpdate(LeaveActionUpdateRequest request, LeaveAction entity) {
    // TODO Employee needs to own it
  }

  @Override
  public void validateDeletion(UUID id) {
    // TODO Employee needs to own it
  }
}
