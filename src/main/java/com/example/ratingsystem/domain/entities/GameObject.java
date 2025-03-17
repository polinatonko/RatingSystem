package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "game_objects")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GameObject extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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
    }
}