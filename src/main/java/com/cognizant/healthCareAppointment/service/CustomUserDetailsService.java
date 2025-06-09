package com.cognizant.healthCareAppointment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.healthCareAppointment.entity.User;
import com.cognizant.healthCareAppointment.repository.UserRepository;
import com.cognizant.healthCareAppointment.security.CustomUserDetails;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user=userrepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		
		/*
		 * The User obj(user) is passed to the CustomUserDetails constructor to set the object,
		 * cuz OBJ has all INFO of the fetched Data from REPO.. HERE the return type of loadUserbyUser.. 
		 * is UserDetails i.e- Interface , and this interface is implemented by the CustomUserDetails
		 * so we can return the obj of the Implemented class !
		 * 
		 */

		
		return new CustomUserDetails(user);
		
	}
	

}
