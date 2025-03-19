package com.example.ratingsystem.service.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * Provides methods for the jwt authentication.
 */
public interface JwtService {
    /**
     * Generates jwt token for provided user.
     *
     * @param user {@link UserDetails}
     * @return token
     */
    String generateToken(UserDetails user);

    /**
     * Extract username from the provided jwt token.
     *
     * @param token jwt token
     * @return username
     */
    String extractUsername(String token);

    /**
     * Extract expiration date from the provided token.
     *
     * @param token jwt token
     * @return {@link Date} - token's expiration date
     */
    Date extractExpiration(String token);

    /**
     * Checks whether token is valid for the provided user.
     *
     * @param token jwt token
     * @param userDetails {@link UserDetails} to check
     * @return {@code true} if token is valid
     */
    boolean isValid(String token, UserDetails userDetails);
}