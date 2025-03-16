package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.dtos.specification.SpecificationCriteria;
import com.example.ratingsystem.domain.entities.GameObject;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.repository.GameObjectRepository;
import com.example.ratingsystem.service.specification.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameObjectService {
    private final SpecificationService<Object> specificationService;
    private final UserService userService;
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

    public List<GameObject> getAll(List<SpecificationCriteria<Object>> specs) {
        return gameObjectRepository.findAll(Specification.allOf(specs.stream()
                .map(specificationService::getSpecification)
                .toList()));
    }

    private void validateSellerAccess(User objectOwner) {
        if (!userService.validateAuthenticatedUser(objectOwner.getId())) {
            throw new AccessDeniedException("Only seller can manage his objects.");
        }
    }
}