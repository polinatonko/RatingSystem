package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.ConfirmEmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepository extends CrudRepository<ConfirmEmailEntity, String> {
}