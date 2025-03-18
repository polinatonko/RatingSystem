package com.example.ratingsystem.service.game;

import com.example.ratingsystem.domain.entities.Game;
import com.example.ratingsystem.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public Game create(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void delete(UUID id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Optional<Game> get(UUID id) {
        return id == null ? Optional.empty() : gameRepository.findById(id);
    }

    @Override
    public List<Game> getAll() { return gameRepository.findAll(); }
}