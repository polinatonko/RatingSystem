package com.example.ratingsystem.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {
    public static UserDetails getAuthenticatedUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof UserDetails details)) {
            return details;
        }
        return null;
    }
}