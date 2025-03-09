package com.example.ratingsystem.domain.dtos.gameobject;

import com.example.ratingsystem.domain.enums.CommentStatus;

import java.util.UUID;

public record GameObjectUpdateDto(UUID id, String title, String text, CommentStatus status, UUID sellerId, UUID gameId) {
}
