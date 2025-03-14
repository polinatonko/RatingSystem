package com.example.ratingsystem.domain.entities;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(user.getDetails().getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getDetails().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getDetails().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}