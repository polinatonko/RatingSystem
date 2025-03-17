package com.example.ratingsystem.service.user;

import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User create(User user);
    boolean exists(String email);
    Optional<User> get(UUID id);
    Optional<User> getByEmail(String email);
    List<User> getAll();
    void enable(String email);
    User update(User user);
    void delete(UUID id);
    void updatePassword(String email, String password);
    List<UserResponseDto> getTopSellers(int count);
    double calculateRating(User user);
    boolean validateAuthenticatedUser(UUID id);
}