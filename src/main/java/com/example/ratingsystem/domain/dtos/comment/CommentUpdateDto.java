package com.example.ratingsystem.domain.dtos.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentUpdateDto {
    private UUID id;
    @Min(1)
    @Max(5)
    private Integer rating;
    private String message;
    private UUID sellerId;
}