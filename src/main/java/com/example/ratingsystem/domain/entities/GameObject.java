package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "game_objects")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameObject extends BasicEntity {
    @Length(max = 100)
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne(optional = false)
    private User user;
    @ManyToOne
    private Game game;

    public GameObject(String title, String text, User user, Game game) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.game = game;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public GameObject(UUID id, String title, String text, User user, Game game, OffsetDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.user = user;
        this.game = game;
        this.createdAt = createdAt;
        this.updatedAt = OffsetDateTime.now();
    }
}