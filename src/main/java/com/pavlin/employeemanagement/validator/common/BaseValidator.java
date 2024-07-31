package com.pavlin.employeemanagement.validator.common;

public interface BaseValidator<T, U> {

  void validateCreation(T request);

  void validateUpdate(T request, U entity);
}
