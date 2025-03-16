package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BasicEntity {
    @Id
    private UUID id;
    @Generated
    @ColumnDefault("false")
    @NotNull
    private boolean isEnabled;

    @OneToOne(
            optional = false,
            cascade = CascadeType.ALL
    )
    @MapsId
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserInfo details;
    @OneToMany(mappedBy = "seller")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<com.example.ratingsystem.domain.entities.Comment> othersComments;
    @OneToMany(mappedBy = "author")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Set<Comment> ownComments;
    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GameObject> objects;

    public User(UserInfo details) {
        this.details = details;
    }
}