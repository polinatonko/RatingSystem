package com.example.ratingsystem.domain.dtos.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateDto {
    private UUID id;
    private String firstName;
    private String lastName;
}