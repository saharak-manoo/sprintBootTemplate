package com.saharak.sprintBootTemplate.exception;

import com.saharak.sprintBootTemplate.models.FailureResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    List<String> list = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage())
        .collect(Collectors.toList());

    FailureResponse<List<String>> response = new FailureResponse<>();
    response.setErrors(list);
    response.setStatus(status.value());

    return handleExceptionInternal(ex, response, headers, status, request);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity handleUserNotFoundException(UserNotFoundException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;

    FailureResponse<String> response = new FailureResponse<>();
    response.setErrors(ex.getMessage());
    response.setStatus(status.value());

    return new ResponseEntity(response, status);
  }

  @ExceptionHandler(UserNotValidationException.class)
  public ResponseEntity UserNotValidationException(UserNotValidationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    FailureResponse<String> response = new FailureResponse<>();
    response.setErrors(ex.getMessage());
    response.setStatus(status.value());

    return new ResponseEntity(response, status);
  }
}