package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.game.GameCreateDto;
import com.example.ratingsystem.domain.dtos.game.GameUpdateDto;
import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.service.GameObjectService;
import com.example.ratingsystem.domain.service.GameService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameObjectService gameObjectService;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<Game> create(@RequestBody GameCreateDto dto) {
        var game = gameService.create(mapper.fromDto(dto));
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(String.format("/{%s}", game.getId()))
                        .buildAndExpand(game.getId()).toUri())
                .body(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> update(@PathVariable UUID id, @RequestBody GameUpdateDto dto) {
        var game = gameService.update(mapper.fromDto(id, dto));
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable UUID id) {
        gameService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> get(@PathVariable UUID id) {
        var game = gameService.get(id);
        return game
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAll() {
        var games = gameService.getAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}/objects")
    public ResponseEntity<List<GameObject>> getObjects(@PathVariable UUID id) {
        var objects = gameObjectService.getByGameId(id);
        return ResponseEntity.ok(objects);
    }
}