package com.example.ratingsystem.util;

import com.example.ratingsystem.domain.dtos.game.GameCreateDto;
import com.example.ratingsystem.domain.dtos.game.GameUpdateDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectCreateDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.service.GameObjectService;
import com.example.ratingsystem.domain.service.GameService;
import com.example.ratingsystem.domain.service.UserService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class Mapper {
    private GameService gameService;
    private GameObjectService gameObjectService;
    private UserService userService;

    public Game fromDto(GameCreateDto dto) {
        return new Game(dto.title(), dto.text());
    }

    public Game fromDto(UUID id, GameUpdateDto dto) {
        var createdAt = gameService.get(id)
                .map(Game::getCreatedAt)
                .orElse(OffsetDateTime.now());
        return new Game(id, dto.title(), dto.text(), createdAt);
    }

    public GameObject fromDto(GameObjectCreateDto dto) {
        var user = getUser(dto.sellerId());
        var game = getGame(dto.gameId());
        return new GameObject(dto.title(), dto.text(), user, game);
    }

    public GameObject fromDto(UUID id, GameObjectUpdateDto dto) {
        var createdAt = gameObjectService.get(id)
                .map(GameObject::getCreatedAt)
                .orElse(OffsetDateTime.now());
        var user = getUser(dto.sellerId());
        var game = getGame(dto.gameId());
        return new GameObject(id, dto.title(), dto.text(), user, game, createdAt);
    }

    private User getUser(UUID id) {
        return userService.get(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Game getGame(UUID id) {
        assert (id != null);
        return gameService.get(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}