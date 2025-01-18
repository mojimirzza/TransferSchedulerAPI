package com.isc.transfer_scheduler.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin")) // Encode password using BCrypt
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2/**") // Disable CSRF for H2 Console
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2/**").permitAll() // Allow access to H2 Console
                        .requestMatchers("/api/v1/**").authenticated() // Secure API endpoints
                        .anyRequest().permitAll() // Allow all other requests
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .disable() // Disable X-Frame-Options for H2 Console
                        )
                )
                .httpBasic(withDefaults()); // Use HTTP Basic authentication
        return http.build();
    }
}