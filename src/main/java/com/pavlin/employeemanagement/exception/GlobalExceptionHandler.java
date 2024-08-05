package com.pavlin.employeemanagement.exception;

import com.pavlin.employeemanagement.exception.common.DuplicateEntryException;
import com.pavlin.employeemanagement.exception.common.NotFoundException;
import com.pavlin.employeemanagement.exception.common.TeamNotEmptyException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = {TeamNotEmptyException.class})
  public ResponseEntity<Object> handleTeamNotEmptyException(TeamNotEmptyException e) {
    logger.info(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }
  
  @ExceptionHandler(value = {DuplicateEntryException.class})
  public ResponseEntity<Object> handleDuplicateEntryException(DuplicateEntryException e) {
    logger.info(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    logger.warn(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    logger.warn(e.getMessage(), e);

    List<String> errorMessages = new ArrayList<>();
    e.getBindingResult()
        .getAllErrors()
        .forEach(error -> errorMessages.add(formatValidationError(error)));

    return new ResponseEntity<>(String.join("\n", errorMessages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleException(Exception e) {
    logger.error(e.getMessage(), e);
    return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private static String formatValidationError(ObjectError error) {
    return ((FieldError) error).getField() + " " + error.getDefaultMessage();
  }
}
