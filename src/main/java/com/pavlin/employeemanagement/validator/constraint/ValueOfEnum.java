package com.pavlin.employeemanagement.validator.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value = {FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
@SuppressWarnings({"java:S1452"})
public @interface ValueOfEnum {
  Class<? extends Enum<?>> enumClass();
  String message() default "must be any of enum {enumClass}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}