package com.example.ratingsystem.service.comment;

import com.example.ratingsystem.domain.dtos.pagination.PageRequestDto;
import com.example.ratingsystem.domain.entities.Comment;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    Comment create(Comment comment);
    Comment update(Comment comment);
    void delete(UUID id);
    Optional<Comment> get(UUID id);
    Page<Comment> getBySellerId(UUID sellerId, PageRequestDto pageRequest);
    Page<Comment> getAll(PageRequestDto pageRequestDto);
}