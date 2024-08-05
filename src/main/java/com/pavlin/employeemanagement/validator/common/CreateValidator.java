package com.pavlin.employeemanagement.validator.common;

public interface CreateValidator<T> {

  void validateCreation(T request);
}
