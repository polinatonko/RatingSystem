package com.example.ratingsystem.domain.dtos.comment;

import com.example.ratingsystem.domain.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private UUID id;
    private int rating;
    private String message;
    private UUID sellerId;
    private UUID authorId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.rating = comment.getDetails().getRating();
        this.message = comment.getDetails().getMessage();
        this.sellerId = comment.getSeller().getId();
        if (comment.getAuthor() != null) {
            this.authorId = comment.getAuthor().getId();
        }
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
