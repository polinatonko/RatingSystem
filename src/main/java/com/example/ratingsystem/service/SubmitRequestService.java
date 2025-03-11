package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.SubmitRequest;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.enums.RequestStatus;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.repository.SubmitRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmitRequestService {
    private final CommentService commentService;
    private final UserService userService;
    private final SubmitRequestRepository requestRepository;

    public SubmitRequest create(SubmitRequest request) {
        // TODO check if user with such email exists
        request.setStatus(RequestStatus.WAITING);
        return requestRepository.save(request);
    }

    public void approve(UUID id) {
        var request = getRequest(id);
        if (request.isProcessed()) {
            return;
        }

        if (request.isRegistration()) {
            var seller = userService.create(new User(request.getUserDetails()));
            request.setSeller(seller);
        }

        if (request.containsComment()) {
            var commentDetails = request.getCommentDetails();
            var comment = new Comment(commentDetails, request.getSeller(), request.getAuthor());
            commentService.create(comment);
        }

        updateStatus(request, RequestStatus.APPROVED);
    }

    public void reject(UUID id) {
        var request = getRequest(id);
        if (request.isProcessed()) {
            return;
        }
        updateStatus(request, RequestStatus.REJECTED);
    }

    private SubmitRequest getRequest(UUID id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private void updateStatus(SubmitRequest request, RequestStatus status) {
        request.setStatus(status);
        requestRepository.save(request);
    }
}