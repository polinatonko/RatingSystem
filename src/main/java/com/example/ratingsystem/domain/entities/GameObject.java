package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "game_objects")
@Data
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
}