package com.learning.blogappapis.security;


import com.learning.blogappapis.exceptions.ResourceNotFoundException;
import com.learning.blogappapis.model.User;
import com.learning.blogappapis.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User byEmailCaseSensitive = this.userRepo.findByEmailCaseSensitive(username);
        if(byEmailCaseSensitive == null){
            System.out.println("No User with Email " + username + " found. Please check the email !!!");
            throw new ResourceNotFoundException("Email","email",username);
        }
        return new CustomUserDetail(byEmailCaseSensitive);
    }
}
