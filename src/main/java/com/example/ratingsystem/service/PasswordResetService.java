package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.Email;
import com.example.ratingsystem.domain.entities.PasswordResetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final JwtService jwtService;
    private final TokenService<PasswordResetEntity, String> passwordResetEntityService;
    private final UserService userService;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public void sendPasswordResetEmail(String email) {
        var userDetails = userService.loadUserByUsername(email);
        checkUserDetailsStatus(userDetails);

        var token = jwtService.generateToken(userDetails);
        saveNewToken(email, token);

        String emailText = """
                A request was received to reset the password for the account with this email. If it wasn't you, ignore this email.
                Link to the password reset: http://localhost:8080/auth/reset.
                Body template: { "token": <token>, "new_passsword": <new_password> }.
                Your token (valid for 24 hours):
                """ + token;
        var mail = new Email(email, "Reset password", emailText);
        emailSender.send(mail);
    }

    public void resetPassword(String token, String newPassword) {
        var username = jwtService.extractUsername(token);
        var userDetails = userService.loadUserByUsername(username);
        checkUserDetailsStatus(userDetails);

        if (!passwordResetEntityService.isValid(userDetails.getUsername(), token)) {
            throw new BadCredentialsException("Invalid token provided");
        }

        var encodedPassword = passwordEncoder.encode(newPassword);
        var email = userDetails.getUsername();
        userService.updatePassword(email, encodedPassword);
    }

    private void saveNewToken(String email, String token) {
        var passwordResetEntity = new PasswordResetEntity(email, token);
        passwordResetEntityService.delete(email);
        passwordResetEntityService.save(passwordResetEntity);
    }

    private void checkUserDetailsStatus(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            throw new DisabledException("Account is disabled");
        }
    }
}