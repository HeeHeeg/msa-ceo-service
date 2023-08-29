package com.example.menu.domain.request;

import com.example.menu.domain.entity.Menu;
import com.example.menu.domain.entity.Store;

public record MenuRequest(String name, Integer price, Long storeId) { //프론트는 이 세개를 넘겨줌
    public Menu toEntity() {
        return Menu.builder()
                .name(name)
                .price(price)
                .store(Store.builder()
                        .id(storeId)
                        .build())
                .build();
    }
}
