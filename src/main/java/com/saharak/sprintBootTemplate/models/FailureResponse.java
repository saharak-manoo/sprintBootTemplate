package com.saharak.sprintBootTemplate.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FailureResponse<T> {
  private T errors;
  private int status;

  public FailureResponse() {
  }

  public FailureResponse(final T errors, final int status) {
    this.errors = errors;
    this.status = status;
  }

  public T getErrors() {
    return errors;
  }

  public void setErrors(final T errors) {
    this.errors = errors;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  @JsonFormat(locale = "th", timezone = "Asia/Bangkok")
  public Date getTimestamp() {
    return new Date();
  }
}