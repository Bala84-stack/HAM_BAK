package com.cognizant.healthCareAppointment.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cognizant.healthCareAppointment.entity.User;
import com.cognizant.healthCareAppointment.service.CustomUserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JWTUtil  {

//	private final String SECRET = "this-is-my-super-secret-key-that-is-long-enough-1234567890!@#";
//	private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
	
	private final long expirationTime = 1000 * 60 * 60; // 1 hour, if last 60 changed to 10 then total -10 Minutes
	
	@Value("${jwt.secret}") 
	private String secret; // Key is set at the APP.PROP file to avoid hardcode
	

	private SecretKey key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String getJwTToken(User user) {
		
		Map<String,Object> claims=new HashMap<>();
		claims.put("role",user.getRole());
		claims.put("ID",user.getUserId());
		claims.put("name",user.getName());
		
		return Jwts.builder()
				.setClaims(claims) //used to add all the data you want to put inside the token
				.setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
}
