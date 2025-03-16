package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.repository.GameObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameObjectService {
    private final AuthService authService;
    private final GameObjectRepository gameObjectRepository;

    public GameObject create(GameObject object) {
        return gameObjectRepository.save(object);
    }

    public GameObject update(GameObject object) {
        validateSellerAccess(object.getUser());
        return gameObjectRepository.save(object);
    }

    public void delete(UUID id) {
        gameObjectRepository.findById(id)
                .ifPresent(object -> {
                    validateSellerAccess(object.getUser());
                    gameObjectRepository.deleteById(id);
                });
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

    private void validateSellerAccess(User objectOwner) {
        if (!authService.validateAuthenticatedUser(objectOwner.getId())) {
            throw new AccessDeniedException("Only seller can manage his objects.");
        }
    }
}