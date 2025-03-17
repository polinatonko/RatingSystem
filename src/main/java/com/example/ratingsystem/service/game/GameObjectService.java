package com.example.ratingsystem.service.game;

import com.example.ratingsystem.domain.dtos.specification.SpecificationCriteria;
import com.example.ratingsystem.domain.entities.GameObject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameObjectService {
    GameObject create(GameObject object);
    GameObject update(GameObject object);
    void delete(UUID id);
    Optional<GameObject> get(UUID id);
    List<GameObject> getByGameId(UUID id);
    List<GameObject> getByUserId(UUID id);
    List<GameObject> getAll(List<SpecificationCriteria<Object>> specs);
}