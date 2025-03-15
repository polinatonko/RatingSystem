package com.example.ratingsystem.domain.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@RedisHash(value = "confirm_email_token", timeToLive = 24 * 60 * 60)
public class ConfirmEmailEntity {
    @Id
    @NotNull
    private String email;
    @NotNull
    private String token;
}