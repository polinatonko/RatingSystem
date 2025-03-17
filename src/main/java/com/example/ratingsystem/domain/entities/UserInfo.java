package com.example.ratingsystem.domain.entities;

import com.example.ratingsystem.domain.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    public UserInfo(String firstName, String lastName, String password, String email, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}