package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.auth.AuthRequestDto;
import com.example.ratingsystem.domain.dtos.auth.TokenResponseDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.service.JwtService;
import com.example.ratingsystem.service.SubmitRequestService;
import com.example.ratingsystem.service.UserService;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final SubmitRequestService requestService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;

    @PostMapping
    @RequestMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubmitResponseDto> register(@RequestBody @Valid UserRequestDto dto) {
        var request = requestService.createRegistrationRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody @NotNull @Valid AuthRequestDto dto) {
        var userDetails = userService.getUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password.");
        }
        var token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new TokenResponseDto(token));
    }
}