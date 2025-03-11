package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.CommentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentDetailsRepository extends JpaRepository<CommentDetails, UUID> {
}
