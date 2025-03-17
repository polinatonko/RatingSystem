package com.example.ratingsystem.service.auth;

public interface PasswordResetService {
    void sendPasswordResetEmail(String email);
    void resetPassword(String token, String newPassword);
}