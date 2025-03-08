package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Game extends BasicEntity {
    @Length(max = 100)
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "game")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GameObject> objects;

    public Game(String title, String text) {
        this.title = title;
        this.text = text;
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    public Game(UUID id, String title, String text, OffsetDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = OffsetDateTime.now();
    }
}