package com.pavlin.employeemanagement.validator;

import com.pavlin.employeemanagement.dto.EmployeeInsertRequest;
import com.pavlin.employeemanagement.dto.EmployeeUpdateRequest;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.validator.common.CreateValidator;
import com.pavlin.employeemanagement.validator.common.DeleteValidator;
import com.pavlin.employeemanagement.validator.common.UpdateValidator;

public interface EmployeeValidator extends
    CreateValidator<EmployeeInsertRequest>,
    UpdateValidator<EmployeeUpdateRequest, Employee>,
    DeleteValidator {

}
