package com.example.ratingsystem.domain.dtos.submitrequest;

import com.example.ratingsystem.domain.entities.CommentDetails;
import com.example.ratingsystem.domain.entities.UserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SubmitCommentAndRegisterRequestDto {
    @Valid
    @NotNull
    private CommentDetails commentDetails;
    @Valid
    @NotNull
    private UserDetails userDetails;
    private UUID authorId;
}