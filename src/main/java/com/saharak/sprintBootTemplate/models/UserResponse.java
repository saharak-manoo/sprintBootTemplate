package com.saharak.sprintBootTemplate.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse implements Serializable {

  @JsonProperty("name")
  private final String name;

  @JsonProperty("surname")
  private final String surname;

  @JsonProperty("date_of_birth")
  @JsonFormat(locale = "th", shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Bangkok")
  private final Date dateOfBirth;

  @JsonProperty("books")
  private List<Long> books = new ArrayList<Long>();

  public UserResponse(final String name, final String surname, Date dateOfBirth, List<Long> books) {
    this.name = name;
    this.surname = surname;
    this.dateOfBirth = dateOfBirth;
    this.books = books;
  }
}