package com.learning.blogappapis.security;

import com.learning.blogappapis.service.Impl.JWTServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter
{

    @Autowired private JWTServiceImpl jwtService;
    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");
        //check for the Authorization field start with the "Bearer" word
        // if found then JWTFilter will work otherwise normal security filter will apply UserNamePassWordAuthFilter
        if(authHeader == null || !authHeader.startsWith("Bearer"))
        {
            filterChain.doFilter(request,response);
            return;
        }
        final String jwtToken = authHeader.substring(7);
        final String userName = jwtService.extractEmail(jwtToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (userName != null && authentication == null)
        {
            System.out.println("Setting authentication for user: {}" +  userName);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwtToken, userDetails))
            {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Authentication set successfully");
            }
            else
            {
                System.out.println("JWT token is invalid for user: {}" + userName);
            }
        }
        else
        {
            System.out.println("No valid JWT token or already authenticated");
        }
        filterChain.doFilter(request, response);
    }
}
