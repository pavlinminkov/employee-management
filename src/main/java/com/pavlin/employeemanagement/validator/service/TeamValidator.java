package com.pavlin.employeemanagement.validator.service;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.validator.service.common.CreateValidator;
import com.pavlin.employeemanagement.validator.service.common.DeleteValidator;

public interface TeamValidator extends
    CreateValidator<TeamRequest>,
    UpdateValidator<TeamRequest, Team>,
    DeleteValidator {

}
