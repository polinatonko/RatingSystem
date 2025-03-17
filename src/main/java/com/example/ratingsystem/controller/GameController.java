package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.game.GameRequestDto;
import com.example.ratingsystem.domain.dtos.game.GameResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.service.game.GameObjectService;
import com.example.ratingsystem.service.game.GameService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameObjectService gameObjectService;
    private final Mapper mapper;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game was created", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public ResponseEntity<GameResponseDto> create(@RequestBody @Valid GameRequestDto dto) {
        var game = gameService.create(mapper.fromDto(dto));
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(game.getId()).toUri())
                .body(toDto(game));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game was updated", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body or path parameter")
    })
    public ResponseEntity<GameResponseDto> update(@PathVariable UUID id, @RequestBody @Valid GameRequestDto dto) {
        dto.setId(id);
        var game = gameService.update(mapper.fromDto(dto));
        return ResponseEntity.ok(toDto(game));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        gameService.delete(id);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game was founded", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Game wasn't found")
    })
    public ResponseEntity<GameResponseDto> get(@PathVariable UUID id) {
        var game = gameService.get(id);
        return game
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "List of games", useReturnTypeSchema = true)
    public ResponseEntity<List<GameResponseDto>> getAll() {
        var games = gameService.getAll();
        return ResponseEntity.ok(games.stream()
                .map(this::toDto)
                .toList());
    }

    @GetMapping("/{id}/objects")
    @ApiResponse(responseCode = "200", description = "List of objects of game", useReturnTypeSchema = true)
    public ResponseEntity<List<GameObjectResponseDto>> getObjects(@PathVariable UUID id) {
        var objects = gameObjectService.getByGameId(id).stream().map(GameObjectResponseDto::new).toList();
        return ResponseEntity.ok(objects);
    }

    private GameResponseDto toDto(Game game) {
        return new GameResponseDto(game);
    }
}