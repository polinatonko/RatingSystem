package com.example.ratingsystem.domain.service;

import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public Optional<User> get(UUID id) {
        return userRepository.findById(id);
    }
}