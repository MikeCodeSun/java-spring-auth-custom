package com.example.auth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControler {
  @Autowired
  private UserRepositor userRepositor;
  @GetMapping("/users")
  public String users(Model model){
    List<User> users = userRepositor.findAll();
    model.addAttribute("users", users);
    return "users";
  }
}
