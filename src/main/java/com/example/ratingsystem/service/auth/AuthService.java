package com.example.ratingsystem.service.auth;

public interface AuthService {
    String login(String email, String password);
    boolean isTokenValid(String token);
    void confirmSignup(String token);
    void sendConfirmationEmail(String email);
}