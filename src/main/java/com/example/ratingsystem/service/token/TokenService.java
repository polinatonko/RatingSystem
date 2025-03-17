package com.example.ratingsystem.service.token;

public interface TokenService<E, K> {
    void save(E entity);
    void delete(K id);
    boolean isValid(K key, String id);
}