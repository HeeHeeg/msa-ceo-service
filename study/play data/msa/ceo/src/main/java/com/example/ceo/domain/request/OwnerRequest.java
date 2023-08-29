package com.example.ceo.domain.request;

import com.example.ceo.domain.entity.Owner;
import com.example.ceo.domain.entity.Store;

public record OwnerRequest(String name, String number) {

    public Owner toEntity() {
        return Owner.builder()
                .name(name)
                .number(number)
                .build();

    }
}
