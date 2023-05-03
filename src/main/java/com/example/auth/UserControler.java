package com.example.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;


@Controller
@RequestMapping("user")
public class UserControler {

  @Autowired
  private UserRepositor userRepositor;
  
  @GetMapping("login")
  public String login(){
    return "user/login";
  }

  @GetMapping("register")
  public String register(Model model){
    model.addAttribute("user", new User());
    return "user/register";
  }

  // @GetMapping("login_failed")
  // public String login_failed(){
  //   return "user/login_failed";
  // }

  // @GetMapping("register_success")
  // public String register_success(){
  //   return "user/register_success";
  // }

  @PostMapping("register_process")
  public String register_process(@Valid @ModelAttribute("user") User user, BindingResult result) {
    if(result.hasErrors()){
      return "user/register";
    }
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.setPassword(encodePassword);
    System.out.println(user);
    userRepositor.save(user);
    return "user/register_success";
  }

}
