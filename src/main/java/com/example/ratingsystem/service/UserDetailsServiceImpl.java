package com.example.ratingsystem.service;

import com.example.ratingsystem.domain.entities.UserDetailsImpl;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByDetailsEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User with email = " + username + " not found"));
        return new UserDetailsImpl(user);
    }
}