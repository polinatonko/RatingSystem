package com.example.ratingsystem.util;

import com.example.ratingsystem.domain.dtos.comment.CommentRequestDto;
import com.example.ratingsystem.domain.dtos.comment.CommentResponseDto;
import com.example.ratingsystem.domain.dtos.comment.CommentUpdateDto;
import com.example.ratingsystem.domain.dtos.game.GameRequestDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectRequestDto;
import com.example.ratingsystem.domain.dtos.gameobject.GameObjectUpdateDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitCommentAndRegisterRequestDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserUpdateDto;
import com.example.ratingsystem.domain.entities.*;
import com.example.ratingsystem.service.comment.CommentService;
import com.example.ratingsystem.service.game.GameObjectService;
import com.example.ratingsystem.service.game.GameService;
import com.example.ratingsystem.service.user.UserService;
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

    public GameObject fromDto(GameObjectUpdateDto dto) {
        GameObject object = gameObjectService.get(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getId()));

        if (dto.getTitle() != null) {
            object.setTitle(dto.getTitle());
        }
        if (dto.getText() != null) {
            object.setText(dto.getText());
        }
        if (dto.getGameId() != null) {
            var game = getGame(dto.getGameId());
            object.setGame(game);
        }
        return object;
    }

    public Comment fromUpdateDto(CommentUpdateDto dto) {
        Comment comment = commentService.get(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException(dto.getId()));
        if (dto.getRating() != null) {
            comment.getDetails().setRating(dto.getRating());
        }
        if (dto.getMessage() != null) {
            comment.getDetails().setMessage(dto.getMessage());
        }
        if (dto.getSellerId() != null) {
            var seller = getUser(dto.getSellerId());
            comment.setSeller(seller);
        }
        return comment;
    }

    public User fromDto(UserUpdateDto dto) {
        var user = getUser(dto.getId());
        if (dto.getFirstName() != null) {
            user.getDetails().setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.getDetails().setLastName(dto.getLastName());
        }
        return user;
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
        var userDetails = new UserInfo(dto.getFirstName(), dto.getLastName(),
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
            if (request.getAuthor() != null) {
                response.authorId(request.getAuthor().getId());
            }
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