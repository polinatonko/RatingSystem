package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Game extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Length(max = 100)
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "game")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GameObject> objects;
}