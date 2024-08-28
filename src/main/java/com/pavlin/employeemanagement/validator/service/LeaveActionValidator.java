package com.pavlin.employeemanagement.validator.service;

import com.pavlin.employeemanagement.dto.LeaveActionInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveActionUpdateRequest;
import com.pavlin.employeemanagement.model.LeaveAction;
import com.pavlin.employeemanagement.validator.service.common.CreateValidator;
import com.pavlin.employeemanagement.validator.service.common.DeleteValidator;

public interface LeaveActionValidator extends
    CreateValidator<LeaveActionInsertRequest>,
    UpdateValidator<LeaveActionUpdateRequest, LeaveAction>,
    DeleteValidator {

}
