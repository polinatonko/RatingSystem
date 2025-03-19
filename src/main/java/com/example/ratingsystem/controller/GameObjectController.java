package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectRequestDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectResponseDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.dtos.specification.SpecificationCriteria;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.exception.InvalidRequestParamValueException;
import com.example.ratingsystem.service.game.GameObjectService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.service.user.UserService;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/objects")
@RequiredArgsConstructor
public class GameObjectController {
    private final UserService userService;
    private final GameObjectService gameObjectService;
    private final Mapper mapper;

    @PostMapping
    @Operation(summary = "Create game object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game object was created", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body"),
            @ApiResponse(responseCode = "403", description = "Declined attempt to create object by no-seller")
    })
    public ResponseEntity<GameObjectResponseDto> createGameObject(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid GameObjectRequestDto dto) {
        var authUser = userService.getByEmail(userDetails.getUsername())
                .orElseThrow(() -> new AccessDeniedException("Only seller can POST new object"));
        dto.setSellerId(authUser.getId());
        var object = gameObjectService.create(mapper.fromDto(dto));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(object.getId()).toUri())
                .body(toDto(object));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partial update of the object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game object was updated", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body or value of path variable")
    })
    public ResponseEntity<GameObjectResponseDto> update(@PathVariable UUID id,
                                                        @RequestBody @Valid GameObjectUpdateDto dto) {
        dto.setId(id);
        var object = gameObjectService.update(mapper.fromDto(dto));
        return ResponseEntity.ok(toDto(object));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete game object via id")
    public void delete(@PathVariable UUID id) {
        gameObjectService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get game object via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game object was founded", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of path variable"),
            @ApiResponse(responseCode = "404", description = "Game object wasn't found")
    })
    public ResponseEntity<GameObjectResponseDto> get(@PathVariable UUID id) {
        var object = gameObjectService.get(id);
        return object
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping
    @Operation(summary = "Get game object by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of filtered objects", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid value of request parameter")
    })
    public ResponseEntity<List<GameObjectResponseDto>> get(@RequestParam(required = false) UUID gameId,
                                                           @RequestParam(required = false, defaultValue = "1") double ratingFrom,
                                                           @RequestParam(required = false, defaultValue = "5") double ratingTo,
                                                           @RequestParam(required = false) String title) {
        if (ratingFrom > ratingTo) {
            throw new InvalidRequestParamValueException("ratingTo should be greater than ratingFrom");
        }
        List<SpecificationCriteria<Object>> specs = new ArrayList<>();
        if (gameId != null) {
            specs.add(SpecificationCriteria.equalsCriteria("game", "id", gameId));
        }
        specs.add(SpecificationCriteria.betweenCriteria("user", "rating", ratingFrom, ratingTo));
        if (title != null) {
            specs.add(SpecificationCriteria.containsCriteria("title", title));
        }
        var objects = gameObjectService.getAll(specs)
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(objects);
    }

    private GameObjectResponseDto toDto(GameObject object) {
        return new GameObjectResponseDto(object);
    }
}