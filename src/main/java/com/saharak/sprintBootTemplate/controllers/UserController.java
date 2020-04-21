package com.saharak.sprintBootTemplate.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.saharak.sprintBootTemplate.exception.UserNotFoundException;
import com.saharak.sprintBootTemplate.exception.UserNotValidationException;
import com.saharak.sprintBootTemplate.models.SuccessResponse;
import com.saharak.sprintBootTemplate.models.User;
import com.saharak.sprintBootTemplate.models.UserResponse;
import com.saharak.sprintBootTemplate.services.LineService;
import com.saharak.sprintBootTemplate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
public class UserController extends ApplicationController {

  @Autowired
  private UserService userService;

  @Autowired
  private LineService lineService;

  // Not JWT
  @ApiOperation(
    value = "Gets users", 
    notes = "Gets all users in database")
  @GetMapping("/users")
  public ResponseEntity<SuccessResponse<List<UserResponse>>> index() throws Exception {
    final SuccessResponse<List<UserResponse>> response = new SuccessResponse<>();
    final List<UserResponse> userResponses = new ArrayList<>();
    userService.all().forEach(user -> userResponses.add(user.asJson()));
    response.setData(userResponses);
    response.setStatus(HttpStatus.OK.value());

    return ResponseEntity.ok(response);
  }

  @ApiOperation(
    value = "Gets user by identifier", 
    notes = "Gets user by identifier in database")
  @GetMapping("/users/{id}")
  public ResponseEntity<SuccessResponse<UserResponse>> show(@PathVariable final Long id) throws Exception {
    final User user = userService.findById(id);
    if (user != null) {
      final SuccessResponse<UserResponse> response = new SuccessResponse<>();
      response.setData(user.asJson());
      response.setStatus(HttpStatus.OK.value());
      lineService.sendNoti(
        MessageFormat.format("มีการเรียกดูข้อมูลของคุณ {0}", user.getFullName())
      );

      return ResponseEntity.ok(response);
    }

    throw new UserNotFoundException("User not found");
  }

  @ApiOperation(
    value = "Updated user by identifier", 
    notes = "Updated user by identifier in database")
  @PutMapping("/users/{id}")
  public ResponseEntity<SuccessResponse<UserResponse>> update(
    @Valid @RequestBody final User user, 
    @PathVariable(value = "id") final Long id) throws Exception {
    try {
      final SuccessResponse<UserResponse> response = new SuccessResponse<>();
      response.setData(userService.update(id, user).asJson());
      response.setStatus(HttpStatus.OK.value());

      return ResponseEntity.ok(response);
    } catch (final DataIntegrityViolationException e) {
      throw new UserNotValidationException("User not validation.");
    }
  }

  @ApiOperation(
    value = "Deleted user by identifier", 
    notes = "Deleted user by identifier in database")
  @DeleteMapping("/users/{id}")
  public ResponseEntity<SuccessResponse<UserResponse>> delete(@PathVariable final Long id) throws Exception {
    final User user = userService.findById(id);

    if (userService.delete(id)) {
      final SuccessResponse<UserResponse> response = new SuccessResponse<>();
      response.setStatus(HttpStatus.OK.value());
      lineService.sendNoti(
        MessageFormat.format("ได้การลบ {0} เรียบร้อยแล้ว", user.getFullName())
      );

      return ResponseEntity.ok(response);
    } else {
      throw new UserNotFoundException("User not found");
    }
  }

  @ApiOperation(
    value = "Gets information about the logged in user", 
    notes = "Gets information about the logged in user")
  @ApiImplicitParam(
    name = "Authorization", 
    value = "Access Token", 
    required = true, 
    allowEmptyValue = false, 
    paramType = "header", 
    example = "Bearer accessToken")
  @GetMapping("/users/information")
  public ResponseEntity<SuccessResponse<UserResponse>> information() throws Exception {
    final User user = userService.findForAuthentication();
    final SuccessResponse<UserResponse> response = new SuccessResponse<>();
    response.setData(user.asJson());
    response.setStatus(HttpStatus.OK.value());

    if (user == null) {
      throw new UserNotFoundException("Username or password incorrect");
    }

    lineService.sendNoti(
      MessageFormat.format("มีการเรียกดูข้อมูลของคุณ {0}", user.getFullName())
    );
    return ResponseEntity.ok(response);
  }

  @ApiOperation(
    value = "Created user", 
    notes = "Created user to database")
  @PostMapping("/users")
  public ResponseEntity<SuccessResponse<UserResponse>> create(@Valid @RequestBody final User userCreate) throws Exception {
    try {
      User user = userService.save(userCreate);
      lineService.sendNoti(
        MessageFormat.format("ได้สร้างผู้ใช้ชื่อ {0} เรียบร้อยแล้ว", user.getFullName())
      );

      final SuccessResponse<UserResponse> response = new SuccessResponse<>();
      response.setData(user.asJson());
      response.setStatus(HttpStatus.CREATED.value());

      return ResponseEntity.ok(response);
    } catch (final DataIntegrityViolationException e) {
      throw new UserNotValidationException("This username is already taken.");
    }
  }

  @ApiOperation(
    value = "Updated information about the logged in user", 
    notes = "Updated information about the logged in user")
  @ApiImplicitParam(
    name = "Authorization", 
    value = "Access Token", 
    required = true, 
    allowEmptyValue = false, 
    paramType = "header", 
    example = "Bearer accessToken")
  @PutMapping("/users")
  public ResponseEntity<SuccessResponse<UserResponse>> updateInfomation(
    @Valid @RequestBody final User userUpdate) throws Exception {
    final User user = userService.findForAuthentication();

    try {
      final SuccessResponse<UserResponse> response = new SuccessResponse<>();
      response.setData(userService.update(user.getId(), userUpdate).asJson());
      response.setStatus(HttpStatus.OK.value());

      return ResponseEntity.ok(response);
    } catch (final DataIntegrityViolationException e) {
      throw new UserNotValidationException("User not validation.");
    }
  }

  @ApiOperation(
    value = "Delete logged in user’s", 
    notes = "Delete logged in user’s")
  @ApiImplicitParam(
    name = "Authorization", 
    value = "Access Token", 
    required = true, 
    allowEmptyValue = false, 
    paramType = "header", 
    example = "Bearer accessToken")
  @DeleteMapping("/users")
  public ResponseEntity<SuccessResponse<UserResponse>> remove() throws Exception {
    final User user = userService.findForAuthentication();

    if ( userService.delete(user.getId())) {
      final SuccessResponse<UserResponse> response = new SuccessResponse<>();
      response.setStatus(HttpStatus.OK.value());
      lineService.sendNoti(
        MessageFormat.format("ได้การลบ {0} เรียบร้อยแล้ว", user.getFullName())
      );

      return ResponseEntity.ok(response);
    } else {
      throw new UserNotFoundException("User not found");
    }
  }
}