package com.example.ratingsystem.domain.dtos.registration;

import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.User;

public record CommentRegistrationResponseDto(Comment comment, User user) {
}
