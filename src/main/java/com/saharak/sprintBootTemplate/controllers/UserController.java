package com.saharak.sprintBootTemplate.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
public class UserController extends ApplicationController {

  @Autowired
  private UserService userService;

  @Autowired
  private LineService lineService;

  @ApiOperation(value = "Gets information about the logged in user", notes = "Gets information about the logged in user")
  @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer accessToken")
  @GetMapping("/users")
  public ResponseEntity<UserResponse> information() throws Exception {
    final User user = userService.findForAuthentication();
    final UserResponse userResponse = new UserResponse(user.getFullName());
    if (user == null) {
      throw new UserNotFoundException("Username or password incorrect");
    }

    lineService.sendNoti(MessageFormat.format("มีการเรียกดูข้อมูลของคุณ {0}", user.getFullName()));
    return ResponseEntity.ok(userResponse);
  }

  @ApiOperation(value = "Created user", notes = "Created user to database")
  @PostMapping("/users")
  public ResponseEntity<String> create(@Valid @RequestBody final User user) throws Exception {
    try {
      userService.save(user);
      lineService.sendNoti(MessageFormat.format("ได้สร้างผู้ใช้ชื่อ {0} เรียบร้อยแล้ว", user.getFullName()));

      return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.OK.toString());
    } catch (final DataIntegrityViolationException e) {
      throw new UserNotValidationException("This username is already taken.");
    }
  }

  @ApiOperation(value = "Delete logged in user’s record and order history", notes = "Delete logged in user’s record and order history")
  @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer accessToken")
  @DeleteMapping("/users")
  public ResponseEntity<String> remove() throws Exception {
    final User user = userService.findForAuthentication();
    if (user != null) {
      userService.delete(user.getId());
      lineService.sendNoti(MessageFormat.format("ได้การลบ {0} เรียบร้อยแล้ว", user.getFullName()));

      return ResponseEntity.ok(HttpStatus.OK.toString());
    } else {
      throw new UserNotFoundException("Username or password incorrect");
    }
  }
  
  // ตัวอย่างแบบไม่ต้องใช้ Token
  @ApiOperation(value = "Gets users", notes = "Gets all users in database")
  @GetMapping("/users")
  public ResponseEntity<List<User>> index() throws Exception {
  return ResponseEntity.ok(userService.all());
  }

  @ApiOperation(value = "Gets user by identifier", notes = "Gets user by identifier in database")
  @GetMapping("/users/{id}")
  public ResponseEntity<?> show(@PathVariable final Long id) throws Exception {
    final User user = userService.findById(id);
    if (user != null) {
      final SuccessResponse<User> response = new SuccessResponse<>();
      response.setData(user);
      response.setStatus(HttpStatus.OK.value());
      lineService.sendNoti(MessageFormat.format("มีการเรียกดูข้อมูลของคุณ {0}", user.getFullName()));

      return ResponseEntity.ok(response);
    }

    throw new UserNotFoundException("Username or password incorrect");
  }

  @ApiOperation(value = "Updated user by identifier", notes = "Updated user by identifier in database")
  @PutMapping("/users/{id}")
  public ResponseEntity<User> update(@Valid @RequestBody final User user, @PathVariable(value = "id") final Long id) throws Exception {
    return ResponseEntity.ok(userService.update(user, id));
  }

  @ApiOperation(value = "Deleted user by identifier", notes = "Deleted user by identifier in database")
  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> delete(@PathVariable final Long id) throws Exception {
    User user = userService.findById(id);

    if (userService.delete(id)) {
      final SuccessResponse<User> response = new SuccessResponse<>();
      response.setStatus(HttpStatus.OK.value());
      lineService.sendNoti(MessageFormat.format("ได้การลบ {0} เรียบร้อยแล้ว", user.getFullName()));

      return ResponseEntity.ok(response);
    } else {
      throw new UserNotFoundException("Username or password incorrect");
    }
  }
}