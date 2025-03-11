package com.example.ratingsystem.util;

import com.example.ratingsystem.domain.dtos.comment.CommentRequestDto;
import com.example.ratingsystem.domain.dtos.game.GameRequestDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitRequestDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.domain.entities.*;
import com.example.ratingsystem.service.CommentService;
import com.example.ratingsystem.service.GameObjectService;
import com.example.ratingsystem.service.GameService;
import com.example.ratingsystem.service.UserService;
import com.example.ratingsystem.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Mapper {
    private final GameObjectService gameObjectService;
    private final GameService gameService;
    private final UserService userService;
    private final CommentService commentService;

    public Game fromDto(GameRequestDto dto) {
        Game game = gameService.get(dto.getId()).orElse(new Game());
        game.setTitle(dto.getTitle());
        game.setText(dto.getText());
        return game;
    }

    public GameObject fromDto(GameObjectRequestDto dto) {
        GameObject object = gameObjectService.get(dto.getId()).orElse(new GameObject());
        var user = getUser(dto.getSellerId());
        var game = getGame(dto.getGameId());
        object.setTitle(dto.getTitle());
        object.setText(dto.getText());
        object.setUser(user);
        object.setGame(game);
        return object;
    }

    public Comment fromUpdateDto(CommentRequestDto dto) {
        Comment comment = commentService.get(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getId()));
        var seller = getUser(dto.getSellerId());
        var author = getUserNullable(dto.getAuthorId());
        comment.setSeller(seller);
        comment.setAuthor(author);
        comment.getDetails().setMessage(dto.getMessage());
        comment.getDetails().setRating(dto.getRating());
        return comment;
    }

    public User fromDto(UserRequestDto dto) {
        var details = new UserDetails(dto.firstName(), dto.lastName(), dto.password(), dto.email(), dto.role());
        return new User(details);
    }

    public SubmitRequest fromDto(SubmitRequestDto dto) {
        var seller = getUserNullable(dto.getSellerId());
        var author = getUserNullable(dto.getAuthorId());
        return new SubmitRequest(dto.getCommentDetails(), dto.getUserDetails(), seller, author);
    }

    private User getUser(UUID id) {
        return userService.get(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private User getUserNullable(UUID id) {
        return id == null ? null : userService.get(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private Game getGame(UUID id) {
        return gameService.get(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}