package com.example.ratingsystem.domain.dtos.user;


import com.example.ratingsystem.domain.enums.UserRole;

public record UserRequestDto(String firstName, String lastName, String password, String email, UserRole role) {
}
