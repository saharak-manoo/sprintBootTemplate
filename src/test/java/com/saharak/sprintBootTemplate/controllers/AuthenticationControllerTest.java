package com.saharak.sprintBootTemplate.controllers;

import com.saharak.sprintBootTemplate.models.User;
import com.saharak.sprintBootTemplate.models.JwtRequest;
import com.saharak.sprintBootTemplate.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticationControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserService userService;

  @Autowired
  ObjectMapper objectMapper;

  @Rollback(true)
  @Test
  public void generateAuthenticationToken() throws Exception {
    String password = "password";
    final User user = new User();
    user.setUsername("SpringBootTestLoginWithJwt");
    user.setPassword(password);
    user.setFirstName("SpringBootLoginWithJwt");
    user.setLastName("Test");
    userService.save(user);

    JwtRequest jwtRequest = new JwtRequest();
    jwtRequest.setUsername(user.getUsername());
    jwtRequest.setPassword(password);

    final ResultActions result = mockMvc.perform(
        post("/login").contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper
                      .writeValueAsBytes(jwtRequest)));

    result.andExpect(status().isOk()).andExpect(jsonPath("$.token_type", is("Bearer")));
  }
}