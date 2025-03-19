package com.example.ratingsystem.domain.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenStatusDto {
    private static final String VALID = "VALID";
    private static final String INVALID = "INVALID";
    private String status;

    public TokenStatusDto(boolean isValid) {
        this.status = isValid ? VALID : INVALID;
    }
}