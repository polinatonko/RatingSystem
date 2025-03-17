package com.example.ratingsystem.service.comment;

import com.example.ratingsystem.domain.entities.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    Comment create(Comment comment);
    Comment update(Comment comment);
    void delete(UUID id);
    Optional<Comment> get(UUID id);
    List<Comment> getBySellerId(UUID sellerId);
}