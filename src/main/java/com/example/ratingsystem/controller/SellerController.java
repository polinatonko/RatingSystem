package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.pagination.PageRequestDto;
import com.example.ratingsystem.domain.dtos.comment.CommentRequestDto;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.dtos.pagination.PageResponseDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.service.comment.CommentService;
import com.example.ratingsystem.service.game.GameObjectService;
import com.example.ratingsystem.service.request.SubmitRequestService;
import com.example.ratingsystem.service.user.UserService;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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
    private static final int DEFAULT_COUNT = 3;

    @PostMapping("/{id}/comments")
    @Operation(summary = "Post comment to the seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request was submitted", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body or value of path variable")
    })
    public ResponseEntity<SubmitResponseDto> create(@PathVariable UUID id, @RequestBody @Validated CommentRequestDto dto) {
        dto.setSellerId(id);
        var request = requestService.createCommentRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Get all comments for the seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of seller's comments", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable or request parameter")
    })
    public ResponseEntity<PageResponseDto<CommentResponseDto>> getSellerComments(@PathVariable UUID id,
                                                                                 @RequestParam(required = false) @Min(1) Integer pageNo,
                                                                                 @RequestParam(required = false) @Min(1) Integer pageSize,
                                                                                 @RequestParam(required = false) String sortDirection,
                                                                                 @RequestParam(required = false) String sortBy) {
        var pageRequest = new PageRequestDto(pageNo, pageSize, sortDirection, sortBy);
        var page = commentService.getBySellerId(id, pageRequest);
        return ResponseEntity.ok(PageResponseDto.from(page, CommentResponseDto::new));
    }

    @GetMapping("/{id}/objects")
    @Operation(summary = "Get all game objects for the seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of seller's game objects", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable")
    })
    public ResponseEntity<List<GameObjectResponseDto>> getGameObjects(@PathVariable UUID id) {
        var objects = gameObjectService.getByUserId(id)
                .stream()
                .map(GameObjectResponseDto::new)
                .toList();
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/top")
    @Operation(summary = "Get list of the top sellers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of top seller's", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of request param")
    })
    public ResponseEntity<List<UserResponseDto>> getTopSellers(
            @RequestParam(value = "count", required = false) Optional<Integer> countOpt
    ) {
        int count = Math.max(1, countOpt.orElse(DEFAULT_COUNT));
        var sellers = userService.getTopSellers(count)
                .stream()
                .map(UserResponseDto::new)
                .toList();
        return ResponseEntity.ok(sellers);
    }
}