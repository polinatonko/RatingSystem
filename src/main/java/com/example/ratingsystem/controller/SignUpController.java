package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.auth.MessageResponseDto;
import com.example.ratingsystem.domain.dtos.auth.TokenDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.service.AuthService;
import com.example.ratingsystem.service.SubmitRequestService;
import com.example.ratingsystem.util.Mapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SubmitRequestService requestService;
    private final Mapper mapper;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<SubmitResponseDto> register(@RequestBody @Valid UserRequestDto dto) {
        var request = requestService.createRegistrationRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @PostMapping
    @RequestMapping("/confirm")
    public ResponseEntity<MessageResponseDto> confirmSignup(@RequestBody @NotNull @Valid TokenDto dto) {
        authService.confirmSignup(dto.token());
        return ResponseEntity.ok(new MessageResponseDto("Email was confirmed"));
    }
}