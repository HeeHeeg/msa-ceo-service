package com.example.menu.domain.response;

import com.example.menu.domain.entity.Menu;

public record MenuResponse(Long id, String name, Integer price) {
    private static MenuResponse of(Menu menu) {
        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice());

    }

}
