package com.saharak.sprintBootTemplate.exception;

public class UserNotValidationException extends RuntimeException {
  public UserNotValidationException(String message) {
    super(message);
  }
}