package com.saharak.sprintBootTemplate.models;

import java.util.Date;

public class SuccessResponse<T> {
  private T data;
  private int status;

  public SuccessResponse() {
  }

  public SuccessResponse(final T data, final int status) {
    this.data = data;
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(final T data) {
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(final int status) {
    this.status = status;
  }

  public Date getTimestamp() {
    return new Date();
  }
}