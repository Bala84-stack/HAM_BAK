package com.cognizant.healthCareAppointment.controller;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authApi")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> generateToken(@Valid @RequestBody LoginRequest authRequest,BindingResult result) {
		
		
		if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
		
		try
		{
		Authentication authencation=authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		
		return ResponseEntity.ok("Authencation succesfull JWT will be generated");
		}
		
		catch(AuthenticationException e)
		{
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Creditoanls");
		}
	}
}


