package com.example.ratingsystem.domain.dtos.gameobject;

import com.example.ratingsystem.domain.entities.GameObject;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class GameObjectResponseDto {
    private UUID id;
    private String title;
    private String text;
    private UUID sellerId;
    private UUID gameId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public GameObjectResponseDto(GameObject object) {
        this.id = object.getId();
        this.title = object.getTitle();
        this.text = object.getText();
        this.sellerId = object.getUser().getId();
        this.gameId = object.getGame().getId();
        this.createdAt = object.getCreatedAt();
        this.updatedAt = object.getUpdatedAt();
    }
}