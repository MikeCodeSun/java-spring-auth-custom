package com.example.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;



@Documented
@Target({ElementType.FIELD,ElementType.METHOD,})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValiditor.class)
public @interface Unique {
  String message();
  Class<?> [] groups () default {};
  Class<? extends Payload> [] payload() default{};
}
