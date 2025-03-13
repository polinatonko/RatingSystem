package com.example.ratingsystem.domain.dtos.user;

import com.example.ratingsystem.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class UserRequestDto {
    private UUID id;
    @Length(max = 50)
    @NotNull
    private String firstName;
    @Length(max = 50)
    @NotNull
    private String lastName;
    @Length(max = 60)
    @NotNull
    private String password;
    @Length(max = 100)
    @Column(unique = true)
    @NotNull
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;
}