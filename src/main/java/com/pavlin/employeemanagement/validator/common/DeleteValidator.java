package com.pavlin.employeemanagement.validator.common;

public interface DeleteValidator<T> {

  void validateDeletion(T entity);
}
