package com.example.ratingsystem.domain.dtos.game;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class GameRequestDto {
    private UUID id;
    @Length(max = 100)
    @NotNull
    private String title;
    private String text;
}