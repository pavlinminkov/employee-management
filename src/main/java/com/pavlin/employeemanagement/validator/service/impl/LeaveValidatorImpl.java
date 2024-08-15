package com.pavlin.employeemanagement.validator.service.impl;

import com.pavlin.employeemanagement.dto.LeaveInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveUpdateRequest;
import com.pavlin.employeemanagement.model.Leave;
import com.pavlin.employeemanagement.validator.service.LeaveValidator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class LeaveValidatorImpl implements LeaveValidator {

  @Override
  public void validateCreation(LeaveInsertRequest request) {
    // Unique
  }

  @Override
  public void validateUpdate(LeaveUpdateRequest request, Leave entity) {

  }

  @Override
  public void validateDeletion(UUID id) {
    // Check if exists
  }
}
