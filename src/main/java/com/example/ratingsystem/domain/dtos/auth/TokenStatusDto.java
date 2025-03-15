package com.example.ratingsystem.domain.dtos.auth;

import jakarta.validation.constraints.NotNull;

public record TokenStatusDto(@NotNull String token) {}