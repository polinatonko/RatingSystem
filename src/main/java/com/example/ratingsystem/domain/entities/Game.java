package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Table(name = "games")
@Data
public class Game extends BasicEntity {
    @Length(max = 100)
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "game")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GameObject> objects;
}