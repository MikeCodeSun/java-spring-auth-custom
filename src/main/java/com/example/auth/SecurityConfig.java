package com.example.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private CustomAuthProvider customAuthProvider;

  // @Bean
  // public UserDetailsService userDetailsService() {
  //   return new CustomUserDetailsService();
  // }

  // @Bean 
  // public BCryptPasswordEncoder bCryptPasswordEncoder() {
  //   return new BCryptPasswordEncoder();
  // }

  // @Bean
  // public DaoAuthenticationProvider daoAuthenticationProvider(){
  //   DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
  //   daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
  //   daoAuthenticationProvider.setUserDetailsService(userDetailsService());
  //   return daoAuthenticationProvider;
  // }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(customAuthProvider);
    return authenticationManagerBuilder.build();
  }
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(az->az.requestMatchers("/user/*").permitAll().anyRequest().authenticated()).formLogin(fl->fl.loginPage("/user/login").defaultSuccessUrl("/users").failureHandler(new AuthenticationFailureHandler(){
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
          AuthenticationException exception) throws IOException, ServletException {
          String error = exception.getMessage();
          request.getSession().setAttribute("LOGIN_FAILED_ERROR", error);
          String redirectUrl = request.getContextPath() + "/user/login?error";
            // System.out.println("url: " +redirectUrl);
            // System.out.println("error: " + error);
          response.sendRedirect(redirectUrl);
      }
    }).permitAll()).logout(lt->lt.logoutUrl("/user/logout").logoutSuccessUrl("/user/login").permitAll()).httpBasic().and().authenticationManager(authenticationManager(http));

    
    return http.build();
  }
}
