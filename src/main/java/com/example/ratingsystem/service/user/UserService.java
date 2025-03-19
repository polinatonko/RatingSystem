package com.example.ratingsystem.service.user;

import com.example.ratingsystem.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service that provides basic operation for managing {@link User} objects.
 */
public interface UserService {
    /**
     * Create new user.
     *
     * @param user {@link User}
     * @return created {@link User}
     */
    User create(User user);

    /**
     * Check whether user with provided email exists.
     *
     * @param email user's email
     * @return {@code true} if user exists
     */
    boolean exists(String email);

    /**
     * Retrieves user via provided id.
     *
     * @param id {@link UUID} - user's id
     * @return {@link Optional} with retrieved user
     */
    Optional<User> get(UUID id);

    /**
     * Retrieves user via provided email.
     *
     * @param email {@link String} - user's email
     * @return {@link Optional} with retrieved user
     */
    Optional<User> getByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return {@link List} with users
     */
    List<User> getAll();

    /**
     * Enable user via provided email.
     *
     * @param email {@link String} - user's email
     */
    void enable(String email);

    /**
     * Updates user.
     *
     * @param user {@link User}
     * @return updated {@link User}
     */
    User update(User user);

    /**
     * Deletes user via provided id.
     *
     * @param id {@link UUID} - user's id
     */
    void delete(UUID id);

    /**
     * Updates user's password.
     *
     * @param email {@link String} - user's email
     * @param password {@link String} - new raw password
     */
    void updatePassword(String email, String password);

    /**
     * Retrieves list of top sellers.
     *
     * @param count seller's count
     * @return {@link List} of users
     */
    List<User> getTopSellers(int count);

    /**
     * Validates id of the authenticated user.
     *
     * @param id {@link UUID} user's id
     * @return {@code true} if authenticated user has provided id
     */
    boolean validateAuthenticatedUser(UUID id);
}