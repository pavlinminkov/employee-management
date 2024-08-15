package com.pavlin.employeemanagement.validator.service;

import com.pavlin.employeemanagement.dto.LeaveInsertRequest;
import com.pavlin.employeemanagement.dto.LeaveUpdateRequest;
import com.pavlin.employeemanagement.model.Leave;
import com.pavlin.employeemanagement.validator.service.common.CreateValidator;
import com.pavlin.employeemanagement.validator.service.common.DeleteValidator;

public interface LeaveValidator extends
    CreateValidator<LeaveInsertRequest>,
    UpdateValidator<LeaveUpdateRequest, Leave>,
    DeleteValidator {

}
