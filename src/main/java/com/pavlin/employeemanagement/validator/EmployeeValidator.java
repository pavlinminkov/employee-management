package com.pavlin.employeemanagement.validator;

import com.pavlin.employeemanagement.dto.EmployeeRequest;
import com.pavlin.employeemanagement.model.Employee;
import com.pavlin.employeemanagement.validator.common.CreateValidator;
import com.pavlin.employeemanagement.validator.common.UpdateValidator;

public interface EmployeeValidator extends
    CreateValidator<EmployeeRequest>,
    UpdateValidator<EmployeeRequest, Employee> {

}
