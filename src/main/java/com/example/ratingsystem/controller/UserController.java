package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.comment.CommentRequestDto;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.service.CommentService;
import com.example.ratingsystem.service.GameObjectService;
import com.example.ratingsystem.service.SubmitRequestService;
import com.example.ratingsystem.service.UserService;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final SubmitRequestService requestService;
    private final UserService userService;
    private final Mapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubmitResponseDto> register(@RequestBody @Valid UserRequestDto dto) {
        var request = requestService.createRegistrationRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

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