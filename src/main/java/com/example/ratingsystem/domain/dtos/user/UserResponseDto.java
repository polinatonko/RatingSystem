package com.example.ratingsystem.domain.dtos.user;

import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.entities.UserInfo;
import com.example.ratingsystem.domain.enums.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private UserRole role;
    private boolean isEnabled;
    private double rating;

    public UserResponseDto(UserInfo details) {
        this.id = details.getId();
        this.firstName = details.getFirstName();
        this.lastName = details.getLastName();
        this.password = details.getPassword();
        this.email = details.getEmail();
        this.role = details.getRole();
    }

    public UserResponseDto(User user) {
        this(user.getDetails());
        this.id = user.getId();
        this.isEnabled = user.isEnabled();
        this.rating = user.getRating();
    }
}