package com.example.ratingsystem.domain.dtos.submitrequest;

import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.enums.RequestStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SubmitResponseDto {
    private UUID id;
    private CommentResponseDto comment;
    private UserResponseDto user;
    private RequestStatus status;
}