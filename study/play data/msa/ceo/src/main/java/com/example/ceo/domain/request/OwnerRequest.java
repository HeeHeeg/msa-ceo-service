package com.example.ceo.domain.request;

import com.example.ceo.domain.entity.Owner;
import com.example.ceo.domain.entity.Store;

import java.util.UUID;

public record OwnerRequest(UUID id, String name, String number) {

    public Owner toEntity() {
        return Owner.builder()
                .id(id)
                .name(name)
                .number(number)
                .build();

    }
}
