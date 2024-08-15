package com.pavlin.employeemanagement.validator.service;

public interface UpdateValidator<T, U> {

  void validateUpdate(T request, U entity);
}
