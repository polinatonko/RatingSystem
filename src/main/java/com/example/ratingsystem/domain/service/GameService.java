package com.example.ratingsystem.domain.service;

import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {
    private GameRepository gameRepository;

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public Game update(Game game) {
        return gameRepository.save(game);
    }

    public void delete(UUID id) {
        gameRepository.deleteById(id);
    }

    public Optional<Game> get(UUID id) {
        return gameRepository.findById(id);
    }

    public List<Game> getAll() { return gameRepository.findAll(); }
}