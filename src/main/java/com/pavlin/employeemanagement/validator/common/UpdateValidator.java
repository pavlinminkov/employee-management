package com.pavlin.employeemanagement.validator.common;

public interface UpdateValidator<T, U> {

  void validateUpdate(T request, U entity);
}
