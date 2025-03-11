package com.example.ratingsystem.domain.dtos.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentRequestDto {
    private UUID id;
    @Min(1)
    @Max(5)
    @NotNull
    private int rating;
    private String message;
    private UUID sellerId;
    private UUID authorId;
}