package com.example.ratingsystem.domain.dtos.comment;

import com.example.ratingsystem.domain.enums.CommentStatus;

import java.util.UUID;

public record CommentUpdateDto(UUID id, int rating, String message, CommentStatus status, UUID sellerId, UUID authorId) {
}