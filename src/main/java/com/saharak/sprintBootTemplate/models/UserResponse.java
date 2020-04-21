package com.saharak.sprintBootTemplate.models;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse implements Serializable {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("username")
  private final String username;

  @JsonProperty("fullName")
  private final String fullName;

  public UserResponse(final Long id, String username, final String fullName) {
    this.id = id;
    this.username = username;
    this.fullName = fullName;
  }
}