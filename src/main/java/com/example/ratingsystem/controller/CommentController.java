package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.comment.CommentStatusDto;
import com.example.ratingsystem.domain.dtos.comment.CommentUpdateDto;
import com.example.ratingsystem.domain.dtos.registration.CommentRegistrationRequestDto;
import com.example.ratingsystem.domain.dtos.registration.CommentRegistrationResponseDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.enums.CommentStatus;
import com.example.ratingsystem.domain.service.CommentService;
import com.example.ratingsystem.domain.service.UserService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<CommentRegistrationResponseDto> submitAndRegister(
            @RequestBody @Validated CommentRegistrationRequestDto dto
    ) {
        var user = userService.create(mapper.fromDto(dto.getUserDto()));
        var comment = commentService.create(mapper.fromDto(user.getId(), dto.getCommentDto()));
        return ResponseEntity.ok(new CommentRegistrationResponseDto(comment, user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable UUID id, @RequestBody @Validated CommentUpdateDto dto) {
        var comment = commentService.update(mapper.fromDto(id, dto));
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        commentService.delete(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Comment> changeStatus(@PathVariable UUID id, @RequestBody @Validated CommentStatusDto dto) {
        var comment = commentService.changeStatus(id, CommentStatus.valueOf(dto.status()));
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> get(@PathVariable UUID id) {
        var comment = commentService.get(id);
        return comment
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}