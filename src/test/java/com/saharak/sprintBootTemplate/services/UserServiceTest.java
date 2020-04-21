package com.saharak.sprintBootTemplate.services;

import com.saharak.sprintBootTemplate.models.User;
import com.saharak.sprintBootTemplate.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

  @Autowired
  private UserRepository userRepository;

  @Rollback(true)
  @Test
  public void save() throws Exception {
    Random rand = new Random();
    User newUser = new User();
    newUser.setUsername("Saharak" + rand.nextInt(5000));
    newUser.setPassword("password");
    newUser.setName("Saharak");
    newUser.setSurname("Manoo");
    newUser.setDateOfBirth(new Date(1997, 04, 03));
    User userSave = userRepository.save(newUser);

    User user = userRepository.findByUsername(userSave.getUsername());
    assertThat(user.getUsername(), is(userSave.getUsername()));
  }
}