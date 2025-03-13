package com.example.ratingsystem.domain.dtos.comment;

import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.CommentDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {
    private UUID id;
    private int rating;
    private String message;
    private UUID sellerId;
    private UUID authorId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this(comment.getDetails());
        this.id = comment.getId();
        this.sellerId = comment.getSeller().getId();
        if (comment.getAuthor() != null) {
            this.authorId = comment.getAuthor().getId();
        }
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public CommentResponseDto(CommentDetails details) {
        this.id = details.getId();
        this.rating = details.getRating();
        this.message = details.getMessage();
    }
}
