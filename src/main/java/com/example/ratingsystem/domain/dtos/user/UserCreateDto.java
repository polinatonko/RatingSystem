package com.example.ratingsystem.domain.dtos.user;


import com.example.ratingsystem.domain.enums.UserRole;

public record UserCreateDto(String firstName, String lastName, String password, String email, UserRole role) {
}
