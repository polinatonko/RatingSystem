package com.example.ratingsystem.service.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {
    String generateToken(UserDetails user);
    String extractUsername(String token);
    Date extractExpiration(String token);
    boolean isValid(String token, UserDetails userDetails);
}