package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.PasswordResetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordResetEntity, String> {
}