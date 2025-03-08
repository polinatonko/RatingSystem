package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.service.GameObjectService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final GameObjectService gameObjectService;
    private final Mapper mapper;

    @GetMapping("/{id}/objects")
    public ResponseEntity<List<GameObject>> getGameObjects(@PathVariable UUID id) {
        var objects = gameObjectService.getByUserId(id);
        return ResponseEntity.ok(objects);
    }
}