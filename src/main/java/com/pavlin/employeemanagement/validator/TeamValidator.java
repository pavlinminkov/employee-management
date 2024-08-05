package com.pavlin.employeemanagement.validator;

import com.pavlin.employeemanagement.dto.TeamRequest;
import com.pavlin.employeemanagement.model.Team;
import com.pavlin.employeemanagement.validator.common.CreateValidator;
import com.pavlin.employeemanagement.validator.common.DeleteValidator;
import com.pavlin.employeemanagement.validator.common.UpdateValidator;

public interface TeamValidator extends
    CreateValidator<TeamRequest>,
    UpdateValidator<TeamRequest, Team>,
    DeleteValidator<Team> {

}
