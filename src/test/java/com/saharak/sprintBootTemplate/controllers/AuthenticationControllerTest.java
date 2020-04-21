package com.saharak.sprintBootTemplate.controllers;

import com.saharak.sprintBootTemplate.models.User;
import com.saharak.sprintBootTemplate.models.JwtRequest;
import com.saharak.sprintBootTemplate.models.JwtResponse;
import com.saharak.sprintBootTemplate.repositories.UserRepository;
import com.saharak.sprintBootTemplate.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.sql.Date;
import java.util.Map;

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
    user.setName("SpringBootLoginWithJwt");
    user.setSurname("Test");
    user.setDateOfBirth(new Date(1997, 04, 03));
    userService.save(user);

    JwtRequest jwtRequest = new JwtRequest();
    jwtRequest.setUsername(user.getUsername());
    jwtRequest.setPassword(password);

    final ResultActions result = mockMvc.perform(
        post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(jwtRequest)));

    result.andExpect(status().isOk()).andExpect(jsonPath("$.token_type", is("Bearer")));
  }
}