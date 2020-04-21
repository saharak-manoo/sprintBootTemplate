package com.saharak.sprintBootTemplate.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @ApiModelProperty(hidden = true)
  private Long id;

  @NotBlank(message = "Username can't be blank")
  @Size(min = 6, max = 100, message = "Username size must be between 6 and 100")
  @Column(name = "username", unique = true)
  @JsonProperty("username")
  private String username;

  @NotBlank(message = "Password can't be blank")
  @Size(min = 6, max = 100, message = "Password size must be between 6 and 100")
  @Column(name = "password")
  @JsonProperty("password")
  private String password;

  @NotBlank(message = "Name can't be blank")
  @Column(name = "name")
  @JsonProperty("name")
  private String name;

  @NotBlank(message = "Surname can't be blank")
  @Column(name = "surname")
  @JsonProperty("surname")
  private String surname;

  @NotNull(message = "Date of birth can't be blank")
  @Column(name = "date_of_birth")
  @JsonProperty("date_of_birth")
  @JsonFormat(locale = "th", shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Bangkok")
  private Date dateOfBirth;

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    this.password = passwordEncoder.encode(password);
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(final Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}