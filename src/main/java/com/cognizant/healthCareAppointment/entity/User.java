package com.cognizant.healthCareAppointment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private String role;

    @Column(unique = true)
    private String email;

    private String phone;

    private String password;
}
