package com.example.auth;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValiditor  implements ConstraintValidator<Unique, String>{

    @Autowired
    public CustomUserDetailsService customUserDetailsService;
    
    @Override
    public void initialize(Unique unique) {
      // ConstraintValidator.super.initialize(constraintAnnotation);
      unique.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
      if(customUserDetailsService != null && customUserDetailsService.existByName(name)){
        return false;
      }
      return true;
    }
}
