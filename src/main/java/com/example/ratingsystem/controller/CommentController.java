package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.comment.CommentUpdateDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitCommentAndRegisterRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.service.comment.CommentService;
import com.example.ratingsystem.service.request.SubmitRequestService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SubmitRequestService requestService;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<SubmitResponseDto> submitAndRegister(
            @RequestBody @Valid SubmitCommentAndRegisterRequestDto dto
    ) {
        var request = requestService.createRegistrationRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody @Validated CommentUpdateDto dto) {
        dto.setId(id);
        var comment = commentService.update(mapper.fromUpdateDto(dto));
        return ResponseEntity.ok(toDto(comment));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        commentService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> get(@PathVariable UUID id) {
        var comment = commentService.get(id);
        return comment
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(comment);
    }
}