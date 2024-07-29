package com.pavlin.employeemanagement.exception;

import com.pavlin.employeemanagement.exception.exception.DuplicateEntryException;
import com.pavlin.employeemanagement.exception.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = {DuplicateEntryException.class})
  public ResponseEntity<Object> handleDuplicateEntryException(DuplicateEntryException e) {
    logger.warn(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    logger.warn(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleException(Exception e) {
    logger.error(e.getMessage(), e);
    return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
