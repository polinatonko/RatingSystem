package com.example.ratingsystem.domain.dtos.auth;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
}