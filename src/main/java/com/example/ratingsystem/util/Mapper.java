package com.example.ratingsystem.util;

import com.example.ratingsystem.domain.dtos.comment.CommentRequestDto;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.game.GameRequestDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitCommentAndRegisterRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
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
        var details = new UserDetails(dto.getFirstName(), dto.getLastName(),
                dto.getPassword(), dto.getEmail(), dto.getRole());
        return new User(details);
    }

    public SubmitRequest toSubmitRequest(SubmitCommentAndRegisterRequestDto dto) {
        var author = getUserNullable(dto.getAuthorId());
        return SubmitRequest.builder()
                .commentDetails(dto.getCommentDetails())
                .userDetails(dto.getUserDetails())
                .author(author)
                .build();
    }

    public SubmitRequest toSubmitRequest(CommentRequestDto dto) {
        var seller = getUserNullable(dto.getSellerId());
        var author = getUserNullable(dto.getAuthorId());
        var commentDetails = new CommentDetails(dto.getRating(), dto.getMessage());
        return SubmitRequest.builder()
                .commentDetails(commentDetails)
                .seller(seller)
                .author(author)
                .build();
    }

    public SubmitRequest toSubmitRequest(UserRequestDto dto) {
        var userDetails = new UserDetails(dto.getFirstName(), dto.getLastName(),
                dto.getPassword(), dto.getEmail(), dto.getRole());
        return SubmitRequest.builder()
                .userDetails(userDetails)
                .build();
    }

    public SubmitResponseDto toSubmitResponseDto(SubmitRequest request) {
        var response = SubmitResponseDto.builder().id(request.getId());
        if (request.getCommentDetails() != null) {
            var comment = commentService.get(request.getCommentDetails().getId())
                    .map(CommentResponseDto::new)
                    .orElse(new CommentResponseDto(request.getCommentDetails()));
            response.comment(comment);
        }
        if (request.getUserDetails() != null) {
            var user = userService.get(request.getUserDetails().getId())
                    .map(UserResponseDto::new)
                    .orElse(new UserResponseDto(request.getUserDetails()));
            response.user(user);
        }
        response.status(request.getStatus());
        return response.build();
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