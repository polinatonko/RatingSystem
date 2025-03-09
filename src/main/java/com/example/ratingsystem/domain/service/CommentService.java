package com.example.ratingsystem.domain.service;

import com.example.ratingsystem.domain.dtos.comment.CommentStatusDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.enums.CommentStatus;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(UUID id) {
        commentRepository.deleteById(id);
    }

    public Optional<Comment> get(UUID id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getBySellerId(UUID sellerId) {
        return commentRepository.findBySellerId(sellerId);
    }

    public Comment changeStatus(UUID id, CommentStatus status) {
        var comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        comment.setStatus(status);
        return commentRepository.save(comment);
    }
}