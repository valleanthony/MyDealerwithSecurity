package com.example.springboot_401;

import javax.persistence.*;

@Entity
@Table(name = "User_Data")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;




}
