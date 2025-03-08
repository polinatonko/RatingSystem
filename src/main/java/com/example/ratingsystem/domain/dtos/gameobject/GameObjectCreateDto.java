package com.example.ratingsystem.domain.dtos.gameobject;

import java.util.UUID;

public record GameObjectCreateDto(String title, String text, UUID sellerId, UUID gameId) {
}
