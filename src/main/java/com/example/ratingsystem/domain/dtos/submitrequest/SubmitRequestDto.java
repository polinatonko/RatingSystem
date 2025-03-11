package com.example.ratingsystem.domain.dtos.submitrequest;

import com.example.ratingsystem.domain.entities.CommentDetails;
import com.example.ratingsystem.domain.entities.UserDetails;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.UUID;

@Data
public class SubmitRequestDto {
    @Valid
    private CommentDetails commentDetails;
    @Valid
    private UserDetails userDetails;
    private UUID sellerId;
    private UUID authorId;
}