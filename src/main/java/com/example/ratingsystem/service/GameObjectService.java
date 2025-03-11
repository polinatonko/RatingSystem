package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.repository.GameObjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameObjectService {
    private GameObjectRepository gameObjectRepository;

    public GameObject create(GameObject dto) {
        return gameObjectRepository.save(dto);
    }

    public GameObject update(GameObject dto) {
        return gameObjectRepository.save(dto);
    }

    public void delete(UUID id) {
        gameObjectRepository.deleteById(id);
    }

    public Optional<GameObject> get(UUID id) {
        return id == null ? Optional.empty() : gameObjectRepository.findById(id);
    }

    public List<GameObject> getByGameId(UUID id) {
        return gameObjectRepository.findByGameId(id);
    }

    public List<GameObject> getByUserId(UUID id) {
        return gameObjectRepository.findByUserId(id);
    }

    public List<GameObject> getAll() {
        return gameObjectRepository.findAll();
    }
}