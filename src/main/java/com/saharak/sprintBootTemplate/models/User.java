package com.saharak.sprintBootTemplate.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.text.MessageFormat;

@Entity
@Table(name = "users")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false)
  @ApiModelProperty(hidden = true)
  private Long id;

  @NotBlank(message = "Username can't be blank")
  @Size(min = 6, max = 100, message = "Username size must be between 6 and 100")
  @Column(name = "username", unique = true, updatable = false)
  @JsonProperty("username")
  private String username;

  @NotBlank(message = "Password can't be blank")
  @Size(min = 6, max = 100, message = "Password size must be between 6 and 100")
  @Column(name = "password", updatable = false)
  @JsonProperty("password")
  private String password;

  @NotBlank(message = "FirstName can't be blank")
  @Column(name = "first_name")
  @JsonProperty("firstName")
  private String firstName;

  @NotBlank(message = "LastName can't be blank")
  @Column(name = "last_name")
  @JsonProperty("lastName")
  private String lastName;

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

  @ApiModelProperty(hidden = true)
  public String getFullName() {
    return MessageFormat.format("{0} {1}", firstName, lastName);
  }

  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public UserResponse asJson() {
    return new UserResponse(this.getId(), this.getUsername(), this.getFullName());
  }
} 