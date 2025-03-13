package com.example.ratingsystem.domain.dtos.user;

import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.entities.UserDetails;
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

    public UserResponseDto(User user) {
        this(user.getDetails());
        this.id = user.getId();
        this.isEnabled = user.isEnabled();
    }

    public UserResponseDto(UserDetails details) {
        this.id = details.getId();
        this.firstName = details.getFirstName();
        this.lastName = details.getLastName();
        this.password = details.getPassword();
        this.email = details.getEmail();
        this.role = details.getRole();
    }
}