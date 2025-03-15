package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.SubmitRequest;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.entities.UserDetails;
import com.example.ratingsystem.domain.enums.RequestStatus;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.exception.UniqueConstraintViolationException;
import com.example.ratingsystem.repository.SubmitRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmitRequestService {
    private final CommentService commentService;
    private final UserService userService;
    private final SubmitRequestRepository requestRepository;
    private final PasswordEncoder passwordEncoder;

    public SubmitRequest createCommentRequest(SubmitRequest request) {
        validateSeller(request.getSeller());
        return updateStatus(request, RequestStatus.WAITING);
    }

    public SubmitRequest createRegistrationRequest(SubmitRequest request) {
        var userDetails= request.getUserDetails();
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        checkUniqueEmail(userDetails);
        if (request.containsComment()) {
            validateSellerRole(userDetails);
        }
        return updateStatus(request, RequestStatus.WAITING);
    }

    public Optional<SubmitRequest> get(UUID id) {
        return requestRepository.findById(id);
    }

    public SubmitRequest approve(UUID id) {
        var request = getRequest(id);
        if (request.isProcessed()) {
            return request;
        }

        checkUniqueEmail(request.getUserDetails());

        if (request.isRegistration()) {
            var seller = userService.create(new User(request.getUserDetails()));
            request.setSeller(seller);
        }

        if (request.containsComment()) {
            var commentDetails = request.getCommentDetails();
            var comment = new Comment(commentDetails, request.getSeller(), request.getAuthor());
            commentService.create(comment);
        }

        return updateStatus(request, RequestStatus.APPROVED);
    }

    public SubmitRequest reject(UUID id) {
        var request = getRequest(id);
        if (request.isProcessed()) {
            return request;
        }
        return updateStatus(request, RequestStatus.REJECTED);
    }

    private SubmitRequest getRequest(UUID id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private void validateSeller(User seller) {
        if (seller == null || seller.getDetails().getRole() != UserRole.ROLE_SELLER) {
            throw new EntityNotFoundException("Seller not found.");
        }
        validateSellerRole(seller.getDetails());
    }

    private void validateSellerRole(UserDetails details) {
        if (details == null || details.getRole() != UserRole.ROLE_SELLER) {
            throw new IllegalArgumentException("Comment can be submitted only to the seller's profile.");
        }
    }

    private void checkUniqueEmail(UserDetails userDetails) {
        if (userDetails != null && userService.exists(userDetails.getEmail())) {
            throw new UniqueConstraintViolationException("User with such email already exists.");
        }
    }

    private SubmitRequest updateStatus(SubmitRequest request, RequestStatus status) {
        request.setStatus(status);
        return requestRepository.save(request);
    }
}