package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.CommentDetails;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.repository.CommentRepository;
import com.example.ratingsystem.repository.UserRepository;
import com.example.ratingsystem.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public boolean exists(String email) {
        return userRepository.existsByDetailsEmail(email);
    }

    public Optional<User> get(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) { return userRepository.findByDetailsEmail(email); }

    public List<User> getAll() { return userRepository.findAll(); }

    public void enable(String email) {
        var user = findByEmailOrThrowException(email);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public User update(User user) {
        validateUserAccess(user);
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    validateUserAccess(user);
                    userRepository.deleteById(id);
                });
    }

    public void updatePassword(String email, String password) {
        var user = findByEmailOrThrowException(email);
        user.getDetails().setPassword(password);
        userRepository.save(user);
    }

    public List<UserResponseDto> getTopSellers(int count) {
        return userRepository.findByDetailsRole(UserRole.ROLE_SELLER)
                .stream()
                .map(user -> new UserResponseDto(user, calculateRating(user)))
                .sorted(Comparator.comparingDouble(UserResponseDto::getRating))
                .limit(count)
                .toList();
    }

    public double calculateRating(User user) {
        return commentRepository.findBySellerId(user.getId())
                .stream()
                .map(Comment::getDetails)
                .mapToInt(CommentDetails::getRating)
                .average()
                .orElse(0);
    }

    public boolean validateAuthenticatedUser(UUID id) {
        var userDetails = AuthUtils.getAuthenticatedUserDetails();
        Optional<User> authUser = userDetails != null
                ? userRepository.findByDetailsEmail(userDetails.getUsername())
                : Optional.empty();
        return authUser.isPresent() && Objects.equals(authUser.get().getId(), id);
    }

    private User findByEmailOrThrowException(String email) {
        return userRepository.findByDetailsEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email = " + email + " not found"));
    }

    private void validateUserAccess(User user) {
        if (!validateAuthenticatedUser(user.getId())) {
            throw new AccessDeniedException("Only user can manage his account");
        }
    }
}