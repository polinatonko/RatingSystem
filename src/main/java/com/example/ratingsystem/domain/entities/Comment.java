package com.example.ratingsystem.domain.entities;

import com.example.ratingsystem.domain.enums.CommentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BasicEntity {
    @Min(1)
    @Max(5)
    @NotNull
    private int rating;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Enumerated(EnumType.STRING)
    @Generated
    @ColumnDefault("WAITING")
    @NotNull
    private CommentStatus status;

    @ManyToOne(optional = false)
    private User seller;
    @ManyToOne
    private User author;

    public Comment(int rating, String message, User seller, User author) {
        this.rating = rating;
        this.message = message;
        this.seller = seller;
        this.author = author;
        this.status = CommentStatus.WAITING;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public Comment(UUID id, int rating, String message, CommentStatus status, User seller, User author, OffsetDateTime createdAt) {
        this.id = id;
        this.rating = rating;
        this.message = message;
        this.status = status;
        this.seller = seller;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = OffsetDateTime.now();
    }
}