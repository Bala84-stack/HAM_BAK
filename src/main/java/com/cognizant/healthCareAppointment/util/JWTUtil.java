package com.cognizant.healthCareAppointment.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	private final String SECRET = "this-is-my-super-secret-key-that-is-long-enough-1234567890!@#";
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String getJwTToken(String email) {
		long expirationTime = 1000 * 60 * 60; // 1 hour
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
}
