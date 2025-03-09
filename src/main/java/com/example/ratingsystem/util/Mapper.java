package com.example.ratingsystem.util;

import com.example.ratingsystem.domain.dtos.comment.CommentCreateDto;
import com.example.ratingsystem.domain.dtos.comment.CommentUpdateDto;
import com.example.ratingsystem.domain.dtos.game.GameCreateDto;
import com.example.ratingsystem.domain.dtos.game.GameUpdateDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectCreateDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.dtos.user.UserCreateDto;
import com.example.ratingsystem.domain.entities.Comment;
import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.service.CommentService;
import com.example.ratingsystem.domain.service.GameObjectService;
import com.example.ratingsystem.domain.service.GameService;
import com.example.ratingsystem.domain.service.UserService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Mapper {
    private final GameService gameService;
    private final GameObjectService gameObjectService;
    private final UserService userService;
    private final CommentService commentService;

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

    public Comment fromDto(UUID sellerId, CommentCreateDto dto) {
        var seller = getUser(sellerId);
        var author = getUser(dto.authorId());
        return new Comment(dto.rating(), dto.message(), seller, author);
    }

    public Comment fromDto(UUID id, CommentUpdateDto dto) {
        var createdAt = commentService.get(id)
                .map(Comment::getCreatedAt)
                .orElse(OffsetDateTime.now());
        var seller = getUser(dto.sellerId());
        var author = getUser(dto.authorId());
        return new Comment(id, dto.rating(), dto.message(), dto.status(), seller, author, createdAt);
    }

    public User fromDto(UserCreateDto dto) {
        return new User(dto.firstName(), dto.lastName(), dto.password(), dto.email(), dto.role());
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