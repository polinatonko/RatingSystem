package com.example.ratingsystem.domain.dtos.submitrequest;

import com.example.ratingsystem.domain.entities.CommentDetails;
import com.example.ratingsystem.domain.entities.SubmitRequest;
import com.example.ratingsystem.domain.entities.UserDetails;
import com.example.ratingsystem.domain.enums.RequestStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitResponseDto {
    private UUID id;
    private CommentDetails commentDetails;
    private UserDetails userDetails;
    private RequestStatus status;
    private UUID sellerId;
    private UUID authorId;

    public SubmitResponseDto(SubmitRequest request) {
        this.id = request.getId();
        this.commentDetails = request.getCommentDetails();
        this.userDetails = request.getUserDetails();
        this.status = request.getStatus();
        if (request.getSeller() != null) {
            this.sellerId = request.getSeller().getId();
        }
        if (request.getAuthor() != null) {
            this.authorId = request.getAuthor().getId();
        }
    }
}