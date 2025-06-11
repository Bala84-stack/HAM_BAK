package com.cognizant.healthCareAppointment.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cognizant.healthCareAppointment.security.CustomUserDetails;
import com.cognizant.healthCareAppointment.service.CustomUserDetailsService;
import com.cognizant.healthCareAppointment.util.JWTUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//We must need to add the JWT AUTHentication before the usernameandpassword auth so we are using this class

@Component
public class JWTAuthFilters extends OncePerRequestFilter {

	@Autowired
	JWTUtil jwtutil;
	
	@Autowired
	CustomUserDetailsService customUserDetailservice;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader=request.getHeader("Authorization"); // GEtting Authoraixation details from header in HTTP
		String token=null;
		String userMailId=null;
		
		try 
		{
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{	
			token=authHeader.substring(7); // de-attaching token from header of http
			// SQUBE SUGG
			
				userMailId=jwtutil.extractMailId(token); // passing token to util class and getting the mailId
			
			  	// One way of doing token verfication done already i.e-using this extractMailid
		}
		
		if(userMailId!=null && SecurityContextHolder.getContext().getAuthentication()==null) 
		{
			CustomUserDetails userDetail= (CustomUserDetails) customUserDetailservice.loadUserByUsername(userMailId);
			
//    ******************** TOKEN VERIFCATION 2nd STAGE************************
			
			if(jwtutil.validateToken(userMailId,userDetail,token)) 
			// If it is True add this to Security COntext
			{
			UsernamePasswordAuthenticationToken authToken=new 
				 UsernamePasswordAuthenticationToken(userDetail, null,userDetail.getAuthorities());
			
			/*
			 * Set all request related details to the authToken, so the Security-context
			 * will have more information regarding the request
			 */
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}			
		}
		
		//Next filter in the filter chain will be called
		filterChain.doFilter(request, response);
		
	}catch(io.jsonwebtoken.ExpiredJwtException e) {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Token expired. Please log in again.");

	}catch (io.jsonwebtoken.JwtException e) {
		/*
		 * JwtException covers: ExpiredJwtException, MalformedJwtException
		 * UnsupportedJwtException and SecurityException (replaces SignatureException)
		 */
	
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Invalid or Malformed TOken");
	}catch(Exception e) {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Authentication Failes");
		
	}
		
		
		
		
	}	
}
