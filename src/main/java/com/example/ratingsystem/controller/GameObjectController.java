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