package com.example.ratingsystem.controller;

import com.example.ratingsystem.domain.dtos.submitrequest.RequestStatusDto;
import com.example.ratingsystem.domain.enums.RequestStatus;
import com.example.ratingsystem.service.SubmitRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class SubmitRequestController {
    private final SubmitRequestService requestService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@PathVariable UUID id, @RequestBody RequestStatusDto statusDto) {
        var status = RequestStatus.valueOf(statusDto.status());
        switch (status) {
            case APPROVED:
                requestService.approve(id);
                break;
            case REJECTED:
                requestService.reject(id);
                break;
            default:
        }
    }
}