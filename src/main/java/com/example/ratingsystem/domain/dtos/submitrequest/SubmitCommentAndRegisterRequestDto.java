package com.example.ratingsystem.domain.dtos.submitrequest;

import com.example.ratingsystem.domain.entities.CommentDetails;
import com.example.ratingsystem.domain.entities.UserInfo;
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
    private UserInfo userDetails;
    private UUID authorId;
}