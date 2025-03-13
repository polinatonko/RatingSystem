package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByDetailsEmail(String email);
    List<User> findByDetailsRole(UserRole role);
}
