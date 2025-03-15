package com.example.ratingsystem.util;

import com.example.ratingsystem.domain.entities.UserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {
    public static UserDetails getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication instanceof UserDetails details) ? details : null;
    }
}