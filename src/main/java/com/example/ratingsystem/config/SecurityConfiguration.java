package com.example.ratingsystem.config;

import com.example.ratingsystem.filter.ExceptionHandlerFilter;
import com.example.ratingsystem.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String gamesEndpoints = "/games/**";
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers(HttpMethod.POST, gamesEndpoints).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, gamesEndpoints).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, gamesEndpoints).hasRole("ADMIN")
                            .requestMatchers("/requests/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/objects").hasRole("SELLER")
                            .anyRequest().permitAll()
            )
                .userDetailsService(userDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionHandlerFilter, AuthorizationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}