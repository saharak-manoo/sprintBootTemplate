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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;
import java.text.MessageFormat;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserService userService;

  @Autowired
  ObjectMapper objectMapper;

  public Map<String, Object> getJwt() throws Exception {
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

    final ResultActions result = mockMvc.perform(post("/api/v1/login")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsBytes(jwtRequest))
    );

    String responseData = result.andReturn()
                                .getResponse()
                                .getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> data = mapper.readValue(responseData, Map.class);

    return data;
  }

  @Rollback(true)
  @Test
  public void information() throws Exception {
    Map<String, Object> jwt = getJwt();

    final ResultActions result = mockMvc
      .perform(get("/api/v1/users/information")
      .header(
        "Authorization", 
        MessageFormat.format("{0} {1}", jwt.get("tokenType"), jwt.get("accessToken")))
      .contentType(MediaType.APPLICATION_JSON)
    );

    result.andExpect(status().isOk())
          .andExpect(jsonPath("$.data.fullName", is("SpringBootLoginWithJwt Test")))
          .andExpect(jsonPath("$.status", is(200)));
          
  }

  @Rollback(true)
  @Test
  public void create() throws Exception {
    final User user = new User();
    user.setUsername("SpringBootTestCreate");
    user.setPassword("password");
    user.setFirstName("TestCreated");
    user.setLastName("Saharak");

    final ResultActions result = mockMvc.perform(post("/api/v1/users")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsBytes(user))
    );

    result.andExpect(status().isOk())
          .andExpect(jsonPath("$.data.fullName", is(user.getFullName())))
          .andExpect(jsonPath("$.status", is(201)));
  }

  @Rollback(true)
  @Test
  public void updateInformation() throws Exception {
    Map<String, Object> jwt = getJwt();
    final User user = new User();
    user.setUsername("SpringBootTestUpdate");
    user.setFirstName("SpringBootUpdated");
    user.setLastName("Saharak");

    final ResultActions result = mockMvc.perform(patch("/api/v1/users")
      .header(
        "Authorization", 
        MessageFormat.format("{0} {1}", jwt.get("tokenType"), jwt.get("accessToken")))
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsBytes(user))
    );

    result.andExpect(status().isOk())
          .andExpect(jsonPath("$.data.fullName", is(user.getFullName())))
          .andExpect(jsonPath("$.status", is(200)));
  }

  @Rollback(true)
  @Test
  public void remove() throws Exception {
    Map<String, Object> jwt = getJwt();

    final ResultActions result = mockMvc.perform(delete("/api/v1/users")
      .header(
        "Authorization", 
        MessageFormat.format("{0} {1}", jwt.get("tokenType"), jwt.get("accessToken")))
      .contentType(MediaType.APPLICATION_JSON)
    );

    result.andExpect(status().isOk())
          .andExpect(jsonPath("$.status", is(200)));
  }
}