package com.example.ratingsystem.domain.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@RedisHash(value = "password_reset_token", timeToLive = 60)
public class PasswordResetEntity {
    @Id
    @NotNull
    private String email;
    @NotNull
    private String token;
}