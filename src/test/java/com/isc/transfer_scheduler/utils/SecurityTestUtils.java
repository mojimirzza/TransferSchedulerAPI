package com.isc.transfer_scheduler.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class SecurityTestUtils {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure key

    /**
     * Simulates a JWT-authenticated user with the given username and roles.
     *
     * @param username The username of the authenticated user.
     * @param roles    The roles assigned to the user.
     */
    public static void mockJwtAuthentication(String username, String... roles) {
        // Create a mock JWT token using jjwt
        String token = Jwts.builder()
                .setSubject(username) // Set the subject (username)
                .claim("roles", String.join(",", roles)) // Add roles as a claim
                .setIssuedAt(new Date()) // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Set expiration (1 day)
                .signWith(SECRET_KEY) // Sign the token with the secret key
                .compact();

        // Create a UserDetails object with the username and roles
        UserDetails userDetails = User.withUsername(username)
                .password("") // No password needed for this mock
                .roles(roles)
                .build();

        // Create an authentication token with the UserDetails and JWT token
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, token, userDetails.getAuthorities());

        // Set the authentication in the SecurityContext
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}