package com.isc.transfer_scheduler.auth.config;

import com.isc.transfer_scheduler.auth.security.CustomUserDetailsService;
import com.isc.transfer_scheduler.auth.security.JwtAuthEntryPoint;
import com.isc.transfer_scheduler.auth.filter.JwtAuthenticationFilter;
import com.isc.transfer_scheduler.auth.security.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthEntryPoint authEntryPoint, CustomUserDetailsService userDetailsService) {
        this.authEntryPoint = authEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for all endpoints (or specific endpoints like /h2/**)
                .csrf(csrf -> csrf.disable())
                // Handle exceptions (e.g., unauthorized access)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authEntryPoint)
                )
                // Set session management to stateless (no sessions)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2/**").permitAll() // Allow access to H2 Console
                        .requestMatchers("/api/auth/**").permitAll() // Allow public access to auth endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Allow access to Swagger UI
                        .requestMatchers("/api/v1/accounts/**").hasRole("USER") // Secure account endpoints
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                // Disable X-Frame-Options for H2 Console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .disable() // Disable X-Frame-Options for H2 Console
                        )
                )
                // Add JWT filter before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtils(), userDetailsService);
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}