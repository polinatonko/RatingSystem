package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.gameobject.GameObjectRequestDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.service.GameObjectService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/objects")
@RequiredArgsConstructor
public class GameObjectController {
    private final GameObjectService gameObjectService;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<GameObjectResponseDto> createGameObject(@RequestBody @Valid GameObjectRequestDto dto) {
        var object = gameObjectService.create(mapper.fromDto(dto));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(object.getId()).toUri())
                .body(toDto(object));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GameObjectResponseDto> update(@PathVariable UUID id,
                                                        @RequestBody @Valid GameObjectUpdateDto dto) {
        dto.setId(id);
        var object = gameObjectService.update(mapper.fromDto(dto));
        return ResponseEntity.ok(toDto(object));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        gameObjectService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameObjectResponseDto> get(@PathVariable UUID id) {
        var object = gameObjectService.get(id);
        return object
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping
    public ResponseEntity<List<GameObjectResponseDto>> get() {
        var objects = gameObjectService.getAll().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(objects);
    }

    private GameObjectResponseDto toDto(GameObject object) {
        return new GameObjectResponseDto(object);
    }
}