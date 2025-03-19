package com.example.ratingsystem.service.user;

import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.repository.UserRepository;
import com.example.ratingsystem.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean exists(String email) {
        return userRepository.existsByDetailsEmail(email);
    }

    @Override
    public Optional<User> get(UUID id) {
        return id != null ? userRepository.findById(id) : Optional.empty();
    }

    @Override
    public Optional<User> getByEmail(String email) { return userRepository.findByDetailsEmail(email); }

    @Override
    public List<User> getAll() { return userRepository.findAll(); }

    @Override
    public void enable(String email) {
        var user = findByEmailOrThrowException(email);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User update(User user) {
        validateUserAccess(user);
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id)
                .ifPresent(user -> {
                    validateUserAccess(user);
                    userRepository.deleteById(id);
                });
    }

    @Override
    public void updatePassword(String email, String password) {
        var user = findByEmailOrThrowException(email);
        user.getDetails().setPassword(password);
        userRepository.save(user);
    }

    @Override
    public List<User> getTopSellers(int count) {
        return userRepository.findByDetailsRole(UserRole.ROLE_SELLER)
                .stream()
                .sorted(Comparator.comparingDouble(User::getRating).reversed())
                .limit(count)
                .toList();
    }

    @Override
    public boolean validateAuthenticatedUser(UUID id) {
        var userDetails = AuthUtils.getAuthenticatedUserDetails();
        Optional<User> authUser = userDetails != null
                ? userRepository.findByDetailsEmail(userDetails.getUsername())
                : Optional.empty();
        return authUser.isPresent() && Objects.equals(authUser.get().getId(), id);
    }

    private User findByEmailOrThrowException(String email) {
        return userRepository.findByDetailsEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email = " + email + " not found"));
    }

    private void validateUserAccess(User user) {
        if (!validateAuthenticatedUser(user.getId())) {
            throw new AccessDeniedException("Only user can manage his account");
        }
    }
}