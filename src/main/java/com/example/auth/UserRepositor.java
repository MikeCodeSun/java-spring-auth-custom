package com.example.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepositor extends JpaRepository<User, Integer> {

  @Query("SELECT u FROM User u WHERE u.name = ?1")
  public User findUserByName(String name);
}
