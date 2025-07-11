package com.cognizant.healthCareAppointment.security;

import com.cognizant.healthCareAppointment.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private final User user;
    

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // lets you access the full User object in your controller
    public User getUser() {
        return user;
    }
    
    public String getUserMailId() {
    	return user.getEmail();     // method implemented for validateToken in JWTUtil
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole())); 
        // --> Added roles here of the current user
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // returns actual password
    }

    @Override
    public String getUsername() {
    return user.getEmail(); //  return actual email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
