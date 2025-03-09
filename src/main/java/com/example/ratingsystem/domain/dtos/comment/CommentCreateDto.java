package com.example.ratingsystem.domain.dtos.comment;

import java.util.UUID;

public record CommentCreateDto(int rating, String message, UUID authorId) {}