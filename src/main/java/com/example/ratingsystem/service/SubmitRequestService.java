package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.*;
import com.example.ratingsystem.domain.enums.RequestStatus;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.exception.UniqueConstraintViolationException;
import com.example.ratingsystem.repository.SubmitRequestRepository;
import com.example.ratingsystem.util.AuthUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmitRequestService {
    private final AuthService authService;
    private final CommentService commentService;
    private final UserService userService;
    private final SubmitRequestRepository requestRepository;
    private final PasswordEncoder passwordEncoder;

    public SubmitRequest createCommentRequest(SubmitRequest request) {
        validateSeller(request.getSeller());
        fillInAuthor(request);
        return updateStatusAndSave(request, RequestStatus.WAITING);
    }

    public SubmitRequest createRegistrationRequest(SubmitRequest request) {
        var userDetails= request.getUserDetails();
        if (userDetails.getRole() == UserRole.ROLE_ADMIN) {
            checkAdminRole();
        }

        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        checkUniqueEmail(userDetails);
        if (request.containsComment()) {
            validateSellerRole(userDetails);
        }
        fillInAuthor(request);
        return updateStatusAndSave(request, RequestStatus.WAITING);
    }

    public Optional<SubmitRequest> get(UUID id) {
        return requestRepository.findById(id);
    }

    public List<SubmitRequest> getByStatus(RequestStatus status) {
        return status != null ? requestRepository.findByStatus(status) : requestRepository.findAll();
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

            var email = request.getUserDetails().getEmail();
            authService.sendConfirmationEmail(email);
        }

        if (request.containsComment()) {
            var commentDetails = request.getCommentDetails();
            var comment = new Comment(commentDetails, request.getSeller(), request.getAuthor());
            commentService.create(comment);
        }

        return updateStatusAndSave(request, RequestStatus.APPROVED);
    }

    public SubmitRequest reject(UUID id) {
        var request = getRequest(id);
        return request.isProcessed() ? request : updateStatusAndSave(request, RequestStatus.REJECTED);
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

    private void validateSellerRole(UserInfo details) {
        if (details == null || details.getRole() != UserRole.ROLE_SELLER) {
            throw new IllegalArgumentException("Comment can be submitted only to the seller's profile.");
        }
    }

    private void checkUniqueEmail(UserInfo userDetails) {
        if (userDetails != null && userService.exists(userDetails.getEmail())) {
            throw new UniqueConstraintViolationException("User with such email already exists.");
        }
    }

    private void checkAdminRole() {
        var authUser = AuthUtils.getAuthenticatedUserDetails();
        if (authUser != null) {
            boolean isAdmin = authUser.getAuthorities().stream()
                    .map(auth -> UserRole.valueOf(auth.getAuthority()))
                    .anyMatch(role -> role == UserRole.ROLE_ADMIN);
            if (!isAdmin) {
                throw new AccessDeniedException("Administrator's privileges required");
            }
        }
    }

    private SubmitRequest updateStatusAndSave(SubmitRequest request, RequestStatus status) {
        request.setStatus(status);
        return requestRepository.save(request);
    }

    private void fillInAuthor(SubmitRequest request) {
        var authUser = AuthUtils.getAuthenticatedUserDetails();
        if (authUser != null) {
            var user = userService.getByEmail(authUser.getUsername())
                    .orElseThrow(() ->
                            new EntityNotFoundException("User with email=" + authUser.getUsername() + " not found"));
            request.setAuthor(user);
        }
    }
}