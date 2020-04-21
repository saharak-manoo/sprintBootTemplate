package com.saharak.sprintBootTemplate.services;

import com.saharak.sprintBootTemplate.exception.UserNotFoundException;
import com.saharak.sprintBootTemplate.models.User;
import com.saharak.sprintBootTemplate.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("userService")
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User findByUsername(final String username) {
    return userRepository.findByUsername(username);
  }

  public User findForAuthentication() {
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    final UserDetails currentUser = (UserDetails) auth.getPrincipal();
    final User user = userRepository.findForAuthentication(currentUser.getUsername(), currentUser.getPassword());
    if (user == null) {
      throw new UserNotFoundException("Username or password incorrect");
    }

    return user;
  }

  public User findById(final Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public List<Long> findBookOrdersById(final Long id) {
    return userRepository.findBookOrdersById(id);
  }

  public List<User> all() {
    return userRepository.findAll();
  }

  public User save(final User user) {
    return userRepository.save(user);
  }

  public User update(final User user, final Long id) {
    final User updateUser = userRepository.findById(id).orElse(null);
    if (updateUser != null) {
      updateUser.setName(user.getName());
      updateUser.setSurname(user.getSurname());
      updateUser.setDateOfBirth(user.getDateOfBirth());
    }
    userRepository.save(updateUser);

    return updateUser;
  }

  public Boolean delete(final Long id) {
    final User delUser = userRepository.findById(id).orElse(null);
    if (delUser != null) {
      userRepository.delete(delUser);
      return true;
    }
    return false;
  }
}