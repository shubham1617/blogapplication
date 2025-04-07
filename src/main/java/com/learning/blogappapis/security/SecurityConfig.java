package com.learning.blogappapis.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity() //For Enabling securing on method wise
public class SecurityConfig {

     /*//InMemory Authentication
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder().username("Shubham").password("{noop}password").roles("ADMIN").build();
        UserDetails user1 = User.builder().username("Test").password("{noop}test").roles("READ").build();
        return new InMemoryUserDetailsManager(user,user1);
    }*/

    @Autowired private CustomUserDetailsService customUserDetailsService;
    @Autowired private JwtAuthFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/v1/posts/**").hasAnyRole("ADMIN","READ")
                                //.requestMatchers(HttpMethod.DELETE,"/api/v1/user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/user/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/api/v1/user/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/user/**").permitAll()
                                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                //Add this class to tell UserName Password filter that i will take care of the authorization using JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Disable session tracking;
        return http.build();
    }

}
