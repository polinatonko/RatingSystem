package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserUpdateDto;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.service.user.UserService;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        var users = userService.getAll();
        return ResponseEntity.ok(users.stream().map(this::toDto).toList());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id,
                                                  @RequestBody @NotNull UserUpdateDto dto) {
        dto.setId(id);
        var user = userService.update(mapper.fromDto(dto));
        return ResponseEntity.ok(toDto(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }

    private UserResponseDto toDto(User user) {
        var dto = new UserResponseDto(user);
        dto.setRating(userService.calculateRating(user));
        return dto;
    }
}