package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.gameobject.GameObjectCreateDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.service.GameObjectService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/objects")
@RequiredArgsConstructor
public class GameObjectController {
    private final GameObjectService gameObjectService;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<GameObject> createGameObject(@RequestBody @Validated GameObjectCreateDto dto) {
        var object = gameObjectService.create(mapper.fromDto(dto));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(object.getId()).toUri())
                .body(object);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameObject> update(@PathVariable UUID id, @RequestBody @Validated GameObjectUpdateDto dto) {
        var object = gameObjectService.update(mapper.fromDto(id, dto));
        return ResponseEntity.ok(object);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        gameObjectService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameObject> get(@PathVariable UUID id) {
        var object = gameObjectService.get(id);
        return object
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GameObject>> get() {
        var objects = gameObjectService.getAll();
        return ResponseEntity.ok(objects);
    }
}