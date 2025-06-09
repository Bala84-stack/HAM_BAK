package com.cognizant.healthCareAppointment.dto;

import java.util.HashMap;
import java.util.Map;

import com.cognizant.healthCareAppointment.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;

//    private String role;
//    private Long id;
//    private String name;
}
