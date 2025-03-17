package com.example.ratingsystem.service.auth;

import com.example.ratingsystem.domain.entities.ConfirmEmailEntity;
import com.example.ratingsystem.domain.entities.Email;
import com.example.ratingsystem.service.token.ConfirmEmailEntityService;
import com.example.ratingsystem.service.user.UserService;
import com.example.ratingsystem.service.email.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ConfirmEmailEntityService confirmEmailService;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Override
    public String login(String email, String password) {
        var userDetails = userDetailsService.loadUserByUsername(email);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("Account is disabled");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        var token = jwtService.generateToken(userDetails);
        confirmEmailService.save(new ConfirmEmailEntity(email, token));
        return token;
    }

    @Override
    public boolean isTokenValid(String token) {
        var username = jwtService.extractUsername(token);
        return confirmEmailService.isValid(username, token);
    }

    @Override
    public void confirmSignup(String token) {
        var email = jwtService.extractUsername(token);
        confirmEmailService.delete(email);
        userService.enable(email);
    }

    @Override
    public void sendConfirmationEmail(String email) {
        var userDetails = userDetailsService.loadUserByUsername(email);
        var token = jwtService.generateToken(userDetails);

        var emailText = """
                Your account was approved by administrator!
                Link to confirm email: http://localhost:8080/auth/confirm.
                Request body template: { "token": <token> }.
                Token value:
                """ + token;
        var mail = new Email(email, "Confirmation link", emailText);
        emailSender.send(mail);
    }
}