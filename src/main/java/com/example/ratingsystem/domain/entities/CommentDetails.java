package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "comment_details")
@Data
@NoArgsConstructor
public class CommentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Min(1)
    @Max(5)
    @NotNull
    private int rating;
    @Column(columnDefinition = "TEXT")
    private String message;

    public CommentDetails(int rating, String message) {
        this.rating = rating;
        this.message = message;
    }
}