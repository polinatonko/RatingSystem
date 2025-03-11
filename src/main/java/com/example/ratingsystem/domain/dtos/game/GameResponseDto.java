package com.example.ratingsystem.domain.dtos.game;

import com.example.ratingsystem.domain.entities.Game;
import lombok.Data;

import java.util.UUID;

@Data
public class GameResponseDto {
    private UUID id;
    private String title;
    private String text;

    public GameResponseDto(Game game) {
        this.id = game.getId();
        this.title = game.getTitle();
        this.text = game.getText();
    }
}