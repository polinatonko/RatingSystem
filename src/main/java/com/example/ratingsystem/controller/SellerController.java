package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.comment.CommentRequestDto;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.service.CommentService;
import com.example.ratingsystem.service.GameObjectService;
import com.example.ratingsystem.service.SubmitRequestService;
import com.example.ratingsystem.service.UserService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final GameObjectService gameObjectService;
    private final CommentService commentService;
    private final SubmitRequestService requestService;
    private final UserService userService;
    private final Mapper mapper;
    private final static int DEFAULT_COUNT = 3;

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubmitResponseDto> create(@PathVariable UUID id, @RequestBody @Validated CommentRequestDto dto) {
        dto.setSellerId(id);
        var request = requestService.createCommentRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getSellerComments(@PathVariable UUID id) {
        var comments = commentService.getBySellerId(id)
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

    @GetMapping("/top")
    public ResponseEntity<List<UserResponseDto>> getTopSellers(
            @RequestParam(value = "count", required = false) Optional<Integer> countOpt
    ) {
        int count = Math.max(1, countOpt.orElse(DEFAULT_COUNT));
        var sellers = userService.getTopSellers(count);
        return ResponseEntity.ok(sellers);
    }
}