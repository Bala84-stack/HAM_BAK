package com.cognizant.healthCareAppointment.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.healthCareAppointment.entity.User;
import com.cognizant.healthCareAppointment.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user=userrepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		
		/*
		 * We are passing User entity mail and password inside the
		 * User(org.springframework.security.core.userdetails.User.User()) so the
		 * credtioanls are sent to the Security groups from here
		 * 
		 */
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),new ArrayList<>());
		
		
	}
	

}
