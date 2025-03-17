package com.example.ratingsystem.service.game;

import com.example.ratingsystem.domain.entities.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {
    Game create(Game game);
    Game update(Game game);
    void delete(UUID id);
    Optional<Game> get(UUID id);
    List<Game> getAll();
}