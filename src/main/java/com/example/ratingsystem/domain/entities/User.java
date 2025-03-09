package com.example.ratingsystem.domain.entities;

import com.example.ratingsystem.domain.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BasicEntity {
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
    @Generated
    @ColumnDefault("false")
    @NotNull
    private boolean isEnabled;

    @OneToMany(mappedBy = "seller")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<com.example.ratingsystem.domain.entities.Comment> othersComments;
    @OneToMany(mappedBy = "author")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Set<Comment> ownComments;
    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GameObject> objects;

    public User(String firstName, String lastName, String password, String email, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }
}