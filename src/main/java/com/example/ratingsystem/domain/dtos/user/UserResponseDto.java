package com.example.ratingsystem.domain.dtos.user;

import com.example.ratingsystem.domain.entities.User;
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
        this.id = user.getId();
        this.firstName = user.getDetails().getFirstName();
        this.lastName = user.getDetails().getLastName();
        this.password = user.getDetails().getPassword();
        this.email = user.getDetails().getEmail();
        this.role = user.getDetails().getRole();
        this.isEnabled = user.isEnabled();
    }
}