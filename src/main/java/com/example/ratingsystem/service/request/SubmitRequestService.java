package com.example.ratingsystem.service.request;

import com.example.ratingsystem.domain.entities.SubmitRequest;
import com.example.ratingsystem.domain.enums.RequestStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubmitRequestService {
    SubmitRequest createCommentRequest(SubmitRequest request);
    SubmitRequest createRegistrationRequest(SubmitRequest request);
    Optional<SubmitRequest> get(UUID id);
    List<SubmitRequest> getByStatus(RequestStatus status);
    SubmitRequest approve(UUID id);
    SubmitRequest reject(UUID id);
}