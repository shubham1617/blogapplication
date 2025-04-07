package com.learning.blogappapis.service;

import com.learning.blogappapis.payloads.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService
{
    String generateToken(UserDTO userDTO);

    String extractEmail(String token);

    boolean isTokenValid(String jwtToken, UserDetails userDetails);
}
