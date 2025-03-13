package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.submitrequest.RequestStatusDto;
import com.example.ratingsystem.domain.dtos.submitrequest.SubmitResponseDto;
import com.example.ratingsystem.domain.enums.RequestStatus;
import com.example.ratingsystem.exception.EntityNotFoundException;
import com.example.ratingsystem.service.SubmitRequestService;
import com.example.ratingsystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class SubmitRequestController {
    private final SubmitRequestService requestService;
    private final Mapper mapper;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SubmitResponseDto> updateStatus(@PathVariable UUID id, @RequestBody RequestStatusDto statusDto) {
        var status = RequestStatus.valueOf(statusDto.status());
        var submitRequest = switch (status) {
            case APPROVED -> requestService.approve(id);
            case REJECTED -> requestService.reject(id);
            default -> requestService.get(id).orElseThrow(() -> new EntityNotFoundException(id));
        };
        return ResponseEntity.ok(mapper.toSubmitResponseDto(submitRequest));
    }
}