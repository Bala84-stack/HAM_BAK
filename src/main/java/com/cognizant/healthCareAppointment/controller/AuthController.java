package com.cognizant.healthCareAppointment.controller;

import com.cognizant.healthCareAppointment.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.healthCareAppointment.dto.LoginRequest;
import com.cognizant.healthCareAppointment.util.JWTUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authApi")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@PostMapping("/authenticate")
	public AuthResponse generateToken(@Valid @RequestBody LoginRequest authRequest, BindingResult result) {
		

		try
		{
		Authentication authencation=authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		
		System.out.println(authencation);
		
		return new AuthResponse(jwtUtil.getJwTToken(authRequest.getEmail()),"Authencation succesfull ");
		
		}
	
		catch(AuthenticationException e)
		{
			throw new RuntimeException("Invalid Token");
		}
	}
}


