package com.example.auth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepositor userRepositor;

  
  public boolean existByName(String name)  {
    User user = userRepositor.findUserByName(name);
    if (user == null) {
      return false;
    }
    return true;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepositor.findUserByName(username);
    if(user == null) {
      throw new UsernameNotFoundException("name: " + username + " not found");
    }
    return new CustomUserDetails(user);
  }
}
