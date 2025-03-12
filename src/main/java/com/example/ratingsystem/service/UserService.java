package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public boolean exists(String email) {
        return userRepository.existsByDetailsEmail(email);
    }

    public Optional<User> get(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> getAll() { return userRepository.findAll(); }
}