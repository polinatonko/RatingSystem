package com.example.ratingsystem.domain.dtos.game;

import java.util.UUID;

public record GameUpdateDto(UUID id, String title, String text) {
}
