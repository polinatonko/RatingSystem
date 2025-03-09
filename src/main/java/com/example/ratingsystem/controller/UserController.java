package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.comment.CommentCreateDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.service.CommentService;
import com.example.ratingsystem.domain.service.GameObjectService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final GameObjectService gameObjectService;
    private final CommentService commentService;
    private final Mapper mapper;

    @PostMapping("/{sellerId}/comments")
    public ResponseEntity<Comment> create(@PathVariable UUID sellerId, @RequestBody @Validated CommentCreateDto dto) {
        var comment = commentService.create(mapper.fromDto(sellerId, dto));
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(comment.getId()).toUri())
                .body(comment);
    }

    @GetMapping("/{sellerId}/comments")
    public ResponseEntity<List<Comment>> getSellerComments(@PathVariable UUID sellerId) {
        var comments = commentService.getBySellerId(sellerId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}/objects")
    public ResponseEntity<List<GameObject>> getGameObjects(@PathVariable UUID id) {
        var objects = gameObjectService.getByUserId(id);
        return ResponseEntity.ok(objects);
    }
}