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
}