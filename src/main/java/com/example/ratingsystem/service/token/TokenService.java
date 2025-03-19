package com.example.ratingsystem.service.token;

/**
 * Service for managing tokens.
 *
 * @param <E> token class type
 * @param <K> token id class type
 */
public interface TokenService<E, K> {
    /**
     * Saves provided token.
     *
     * @param entity token
     */
    void save(E entity);

    /**
     * Deletes token via provided id.
     *
     * @param id id of the token
     */
    void delete(K id);

    /**
     * Checks whether the provided token is valid.
     *
     * @param key key to search
     * @param token token value
     * @return {@code true} if token with provided key exists and equals to the provided value
     */
    boolean isValid(K key, String token);
}