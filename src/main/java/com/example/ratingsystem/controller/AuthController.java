package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.auth.*;
import com.example.ratingsystem.service.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Loggable
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @NotNull @Valid AuthRequestDto dto) {
        var token = authService.login(dto.email(), dto.password());
        return ResponseEntity.ok(new TokenDto(token));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<MessageResponseDto> forgotPassword(@RequestBody @NotNull @Valid ForgotPasswordDto dto) {
        passwordResetService.sendPasswordResetEmail(dto.email());
        return ResponseEntity.ok(toMessageResponseDto("Password reset token has sent to the provided email"));
    }

    @PostMapping("/reset")
    public ResponseEntity<MessageResponseDto> resetPassword(@RequestBody @NotNull @Valid ResetPasswordDto dto) {
        passwordResetService.resetPassword(dto.token(), dto.newPassword());
        return ResponseEntity.ok(toMessageResponseDto("Password has successfully reset"));
    }

    @GetMapping("/check_code")
    public ResponseEntity<TokenStatusDto> checkToken(@RequestBody @NotNull @Valid TokenStatusDto dto) {
        var status = authService.isTokenValid(dto.token()) ? "VALID" : "INVALID";
        return ResponseEntity.ok(new TokenStatusDto(status));
    }

    private MessageResponseDto toMessageResponseDto(String message) {
        return new MessageResponseDto(message);
    }
}