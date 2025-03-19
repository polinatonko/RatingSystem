package com.example.ratingsystem.service.auth;

/**
 * Provides basic methods for the authentication.
 */
public interface AuthService {
    /**
     * Log in user with provided credentials and returns token.
     *
     * @param email user email
     * @param password user password
     * @return token
     */
    String login(String email, String password);

    /**
     * Finds out if the provided token is valid.
     *
     * @param token token to check
     * @return {@code true} if the token is valid
     */
    boolean isTokenValid(String token);

    /**
     * Confirms user registration based on the provided token.
     *
     * @param token verification token
     */
    void confirmSignup(String token);

    /**
     * Sends email with confirmation token and link to the user.
     *
     * @param email users' email
     */
    void sendConfirmationEmail(String email);
}