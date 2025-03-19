package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.auth.MessageResponseDto;
import com.example.ratingsystem.domain.dtos.auth.TokenDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserRequestDto;
import com.example.ratingsystem.service.auth.AuthService;
import com.example.ratingsystem.service.request.SubmitRequestService;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Loggable
@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {
    private final SubmitRequestService requestService;
    private final Mapper mapper;
    private final AuthService authService;

    @PostMapping
    @Operation(summary = "Submit request with details for the registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request was submitted", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body")
    })
    public ResponseEntity<SubmitResponseDto> register(@RequestBody @Valid UserRequestDto dto) {
        var request = requestService.createRegistrationRequest(mapper.toSubmitRequest(dto));
        return ResponseEntity.ok(mapper.toSubmitResponseDto(request));
    }

    @PostMapping("/confirm")
    @Operation(summary = "Confirm email after successful registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email was confirmed"),
            @ApiResponse(responseCode = "500", description = "Invalid token")
    })
    public ResponseEntity<MessageResponseDto> confirmSignup(@RequestBody @NotNull @Valid TokenDto dto) {
        authService.confirmSignup(dto.token());
        return ResponseEntity.ok(new MessageResponseDto("Email was confirmed"));
    }
}