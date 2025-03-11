package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.exception.InvalidRequestBodyException;
import com.example.ratingsystem.service.CommentService;
import com.example.ratingsystem.service.GameObjectService;
import com.example.ratingsystem.service.SubmitRequestService;
import com.example.ratingsystem.service.UserService;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final GameObjectService gameObjectService;
    private final SubmitRequestService requestService;
    private final CommentService commentService;
    private final UserService userService;
    private final Mapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubmitResponseDto> register(@RequestBody @Valid SubmitRequestDto dto) {
        if (dto.getUserDetails() == null) {
            throw new InvalidRequestBodyException("Body should contains new user details");
        }
        var request = requestService.create(mapper.fromDto(dto));
        return ResponseEntity.ok(new SubmitResponseDto(request));
    }


    @PostMapping("/{sellerId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubmitResponseDto> create(@PathVariable UUID sellerId, @RequestBody @Validated SubmitRequestDto dto) {
        if (dto.getCommentDetails() == null) {
            throw new InvalidRequestBodyException("Body should contains new comment details");
        }
        dto.setSellerId(sellerId);
        var request = requestService.create(mapper.fromDto(dto));
        return ResponseEntity.ok(new SubmitResponseDto(request));
    }

    @GetMapping("/{sellerId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getSellerComments(@PathVariable UUID sellerId) {
        var comments = commentService.getBySellerId(sellerId)
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}/objects")
    public ResponseEntity<List<GameObjectResponseDto>> getGameObjects(@PathVariable UUID id) {
        var objects = gameObjectService.getByUserId(id)
                .stream()
                .map(GameObjectResponseDto::new)
                .toList();
        return ResponseEntity.ok(objects);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        var users = userService.getAll();
        return ResponseEntity.ok(users.stream().map(UserResponseDto::new).toList());
    }
}