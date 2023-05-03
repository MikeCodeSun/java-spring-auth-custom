package com.example.auth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String name = authentication.getName();
    String password = (String)authentication.getCredentials();
    // System.out.println("name : " + name);
    // System.out.println("password : " + password);
    if(name.isEmpty() || password.isEmpty()) {
      throw new BadCredentialsException("Field must not be empty");
    }

    CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(name);
  
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    boolean isValid = bc.matches(password, customUserDetails.getPassword());
    if(!isValid) {
      throw new BadCredentialsException("password not right!");
    }
    
    return new UsernamePasswordAuthenticationToken(customUserDetails, password, null);
  }
  @Override
  public boolean supports(Class<?> authentication) {
    
    return true;
  }
}
