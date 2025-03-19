package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.user.UserResponseDto;
import com.example.ratingsystem.domain.dtos.user.UserUpdateDto;
import com.example.ratingsystem.domain.entities.User;
import com.example.ratingsystem.service.user.UserService;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users", useReturnTypeSchema = true)
    public ResponseEntity<List<UserResponseDto>> getAll() {
        var users = userService.getAll();
        return ResponseEntity.ok(users.stream().map(this::toDto).toList());
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partial update of the user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body or value of path variable"),
            @ApiResponse(responseCode = "403", description = "Lack of privileges"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id,
                                                  @RequestBody @NotNull UserUpdateDto dto) {
        dto.setId(id);
        var user = userService.update(mapper.fromDto(dto));
        return ResponseEntity.ok(toDto(user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid body or value of path variable"),
            @ApiResponse(responseCode = "403", description = "Lack of privileges")
    })
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(user);
    }
}