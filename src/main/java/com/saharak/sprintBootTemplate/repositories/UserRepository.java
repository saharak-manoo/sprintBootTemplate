package com.saharak.sprintBootTemplate.repositories;

import java.util.List;

import com.saharak.sprintBootTemplate.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
  @Query(
    value = "SELECT * FROM users WHERE users.username = :username", 
    nativeQuery = true)
  User findByUsername(@Param("username") String username);

  @Query(
    value = "SELECT * FROM users WHERE users.username = :username AND users.password = :password", 
    nativeQuery = true)
  User findForAuthentication(@Param("username") String username, @Param("password") String password);

  @Query(
    value = "SELECT DISTINCT orders.book_id FROM orders INNER JOIN user_orders ON user_orders.user_id = :id ORDER BY orders.book_id ASC", 
    nativeQuery = true)
  List<Long> findBookOrdersById(@Param("id") Long id);
}