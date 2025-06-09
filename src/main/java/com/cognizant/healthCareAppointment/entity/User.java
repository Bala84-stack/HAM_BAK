package com.cognizant.healthCareAppointment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	/*
	 * it is not necessary to make User entity implement UserDetails — as long
	 * as we are using a separate UserDetailsService (like your
	 * CustomUserDetailsService) to convert our User entity into a Spring Security
	 * UserDetails object.
	 * --------------------------------------------------------------------------------------
	 * Pros: 
	 * Less boilerplate in CustomUserDetailsService 
	 * Cleaner if app is small and tightly coupled with Spring Security 
	 * 
	 * Cons: 
	 * your entity becomes tightly coupled with Spring Security You have to implement and 
	 * maintain all.
	 * UserDetails methods (getAuthorities(), isAccountNonExpired(), etc.) Not ideal
	 * for larger or layered applications
	 */
	
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
