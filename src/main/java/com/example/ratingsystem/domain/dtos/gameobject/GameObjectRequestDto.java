package com.example.ratingsystem.domain.dtos.gameobject;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class GameObjectRequestDto {
    private UUID id;
    @Length(max = 100)
    @NotNull
    private String title;
    private String text;
    private UUID sellerId;
    @NotNull
    private UUID gameId;
}