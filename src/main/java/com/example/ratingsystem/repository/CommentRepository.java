package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findBySellerId(UUID sellerId, Pageable pageable);
    Page<Comment> findAll(Pageable pageable);
}
