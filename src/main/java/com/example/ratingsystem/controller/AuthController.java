package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.auth.*;
import com.example.ratingsystem.service.auth.AuthService;
import com.example.ratingsystem.service.auth.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Login using email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body"),
            @ApiResponse(responseCode = "401", description = "Bad credentials") // check
    })
    public ResponseEntity<TokenDto> login(@RequestBody @NotNull @Valid AuthRequestDto dto) {
        var token = authService.login(dto.email(), dto.password());
        return ResponseEntity.ok(new TokenDto(token));
    }

    @PostMapping("/forgot_password")
    @Operation(summary = "Obtain code for password reset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset token was sent", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body"),
            @ApiResponse(responseCode = "404", description = "Username not found")
    })
    public ResponseEntity<MessageResponseDto> forgotPassword(@RequestBody @NotNull @Valid ForgotPasswordDto dto) {
        passwordResetService.sendPasswordResetEmail(dto.email());
        return ResponseEntity.ok(toMessageResponseDto("Password reset token has sent to the provided email"));
    }

    @PostMapping("/reset")
    @Operation(summary = "Reset password using code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password was reset", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public ResponseEntity<MessageResponseDto> resetPassword(@RequestBody @NotNull @Valid ResetPasswordDto dto) {
        passwordResetService.resetPassword(dto.token(), dto.newPassword());
        return ResponseEntity.ok(toMessageResponseDto("Password has successfully reset"));
    }

    @GetMapping("/check_code")
    @Operation(summary = "Check whether status of token is valid or invalid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status was sent", useReturnTypeSchema = true)
    })
    public ResponseEntity<TokenStatusDto> checkToken(@RequestBody @NotNull @Valid TokenStatusDto dto) {
        var status = authService.isTokenValid(dto.token()) ? "VALID" : "INVALID";
        return ResponseEntity.ok(new TokenStatusDto(status));
    }

    private MessageResponseDto toMessageResponseDto(String message) {
        return new MessageResponseDto(message);
    }
}