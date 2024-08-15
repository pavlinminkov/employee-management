package com.pavlin.employeemanagement.validator.service.common;

import com.pavlin.employeemanagement.dto.EmployeeInsertRequest;
import com.pavlin.employeemanagement.dto.EmployeeUpdateRequest;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.validator.service.UpdateValidator;

public interface EmployeeValidator extends
    CreateValidator<EmployeeInsertRequest>,
    UpdateValidator<EmployeeUpdateRequest, Employee>,
    DeleteValidator {

}
