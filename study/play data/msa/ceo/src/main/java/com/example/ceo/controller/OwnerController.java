package com.example.ceo.controller;

import com.example.ceo.domain.request.OwnerRequest;
import com.example.ceo.domain.response.OwnerResponse;
import com.example.ceo.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owner")
public class OwnerController {
    private final OwnerService ownerService;
    @GetMapping("/{ownerId}")
    public OwnerResponse getById(@PathVariable UUID ownerId) {
        return ownerService.getById(ownerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postOwner(@RequestBody OwnerRequest request) {
        ownerService.save(request);
    }
}
