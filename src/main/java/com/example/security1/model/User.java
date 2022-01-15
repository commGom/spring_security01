package com.example.security1.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {
    //primary key
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;    //ROLE_USER, ROLE_ADMIN, ROLE_MANAGER
    @CreationTimestamp
    private Timestamp createDate;


}
