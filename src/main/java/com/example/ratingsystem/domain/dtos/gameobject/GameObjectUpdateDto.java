package com.example.ratingsystem.domain.dtos.gameobject;

import lombok.Data;

import java.util.UUID;

@Data
public class GameObjectUpdateDto {
    private UUID id;
    private String title;
    private String text;
    private UUID gameId;
}