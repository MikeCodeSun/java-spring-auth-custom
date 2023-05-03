package com.example.auth;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotBlank(message = "Name must not be mepty")
  @Column(unique = true)
  @Size(min = 3, max = 10, message = "Name must between 3 and 10")
  @Unique(message = "user name already exist")
  private String name;

  @NotBlank(message = "Password must not be empty!")
  @Size(min=6, max = 100, message = "password must more than 6!")
  private String password;

  @CreationTimestamp
  private Date created_at;
}
