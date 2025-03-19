package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.comment.CommentUpdateDto;
import com.example.ratingsystem.domain.dtos.pagination.PageRequestDto;
import com.example.ratingsystem.domain.dtos.pagination.PageResponseDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitCommentAndRegisterRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.service.comment.CommentService;
import com.example.ratingsystem.service.request.SubmitRequestService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    @Operation(summary = "Submit request for comment and user creation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request was submitted", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Declined attempt to create admin")
    })
    public ResponseEntity<SubmitResponseDto> submitAndRegister(
            @RequestBody @Valid SubmitCommentAndRegisterRequestDto dto
    ) {
        var request = requestService.createRegistrationRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update comment via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment was updated", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body or value of path variable"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Declined attempt to update comment")
    })
    public ResponseEntity<CommentResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody @Validated CommentUpdateDto dto) {
        dto.setId(id);
        var comment = commentService.update(mapper.fromUpdateDto(dto));
        return ResponseEntity.ok(toDto(comment));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete comment via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment doesn't exists or was deleted"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Declined attempt to delete comment")
    })
    public void delete(@PathVariable UUID id) {
        commentService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment was founded", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Comment wasn't found")
    })
    public ResponseEntity<CommentResponseDto> get(@PathVariable UUID id) {
        var comment = commentService.get(id);
        return comment
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping
    @Operation(summary = "Get all comments with sorting and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments returned", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of request parameter")
    })
    public ResponseEntity<PageResponseDto<CommentResponseDto>> getAll(@RequestParam(required = false) @Min(1) Integer pageNo,
                                                                      @RequestParam(required = false) @Min(1) Integer pageSize,
                                                                      @RequestParam(required = false) String sortDirection,
                                                                      @RequestParam(required = false) String sortBy) {
        var pageRequest = new PageRequestDto(pageNo, pageSize, sortDirection, sortBy);
        var page = commentService.getAll(pageRequest);
        return ResponseEntity.ok(PageResponseDto.from(page, CommentResponseDto::new));
    }

    private CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(comment);
    }
}