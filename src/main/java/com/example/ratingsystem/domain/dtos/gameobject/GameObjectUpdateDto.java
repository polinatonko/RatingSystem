package com.example.ratingsystem.domain.dtos.gameobject;

import java.util.UUID;

public record GameObjectUpdateDto(UUID id, String title, String text, UUID sellerId, UUID gameId) {
}
