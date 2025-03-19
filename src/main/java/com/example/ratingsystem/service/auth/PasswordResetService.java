package com.example.ratingsystem.service.auth;

/**
 * Provides methods for the password reset process.
 */
public interface PasswordResetService {
    /**
     * Send email to the user with token and link to the password reset endpoint.
     *
     * @param email user's email
     */
    void sendPasswordResetEmail(String email);

    /**
     * Resets user's email.
     *
     * @param token user's token
     * @param newPassword new password value
     */
    void resetPassword(String token, String newPassword);

    /**
     * Finds out if the provided token is valid.
     *
     * @param token token to check
     * @return {@code true} if the token is valid
     */
    boolean isValid(String token);
}