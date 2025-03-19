package com.example.ratingsystem.controller;

import com.example.ratingsystem.aspect.Loggable;
import com.example.ratingsystem.domain.dtos.submitrequest.RequestStatusDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.enums.RequestStatus;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.exception.InvalidRequestParamValueException;
import com.example.ratingsystem.service.request.SubmitRequestService;
import com.example.ratingsystem.util.Mapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Loggable
@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class SubmitRequestController {
    private final SubmitRequestService requestService;
    private final Mapper mapper;

    @PostMapping("/{id}")
    @Operation(summary = "Update status of the request via id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status was changed", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid body or value of path variable"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public ResponseEntity<SubmitResponseDto> updateStatus(@PathVariable UUID id, @RequestBody RequestStatusDto statusDto) {
        var status = RequestStatus.valueOf(statusDto.status());
        var submitRequest = switch (status) {
            case APPROVED -> requestService.approve(id);
            case REJECTED -> requestService.reject(id);
            default -> requestService.get(id).orElseThrow(() -> new EntityNotFoundException(id));
        };
        return ResponseEntity.ok(mapper.toSubmitResponseDto(submitRequest));
    }

    @GetMapping
    @Operation(summary = "Get all request via status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of requests with specific status", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid path parameter value")
    })
    public ResponseEntity<List<SubmitResponseDto>> get(@PathParam("status") String status) {
        var statusEnum = RequestStatus.findByName(status);
        if (status != null && statusEnum == null) {
            throw new InvalidRequestParamValueException(
                    "Invalid value of status: expected WAITING, REJECTED, APPROVED or null");
        }
        var requests = requestService.getByStatus(statusEnum).stream()
                .map(mapper::toSubmitResponseDto)
                .toList();
        return ResponseEntity.ok(requests);
    }
}