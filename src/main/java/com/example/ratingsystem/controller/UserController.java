package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        var users = userService.getAll();
        return ResponseEntity.ok(users.stream().map(this::toDto).toList());
    }

    private UserResponseDto toDto(User user) {
        var dto = new UserResponseDto(user);
        dto.setRating(userService.calculateRating(user));
        return dto;
    }
}