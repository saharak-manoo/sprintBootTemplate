package com.saharak.sprintBootTemplate.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse implements Serializable {

  @JsonProperty("fullName")
  private final String fullName;

  public UserResponse(final String fullName) {
    this.fullName = fullName;
  }
}