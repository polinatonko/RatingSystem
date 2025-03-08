package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, UUID> {
    List<GameObject> findByGameId(UUID id);
    List<GameObject> findByUserId(UUID id);
}
