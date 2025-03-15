package com.example.ratingsystem.domain.dtos.auth;

import jakarta.validation.constraints.NotNull;

public record ResetPasswordDto(@NotNull String token, @NotNull String newPassword) {}