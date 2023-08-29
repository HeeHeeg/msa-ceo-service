package com.example.menu.controller;

import com.example.menu.domain.request.MenuRequest;
import com.example.menu.domain.response.MenuResponse;
import com.example.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postMenu(@RequestBody MenuRequest request) {
        menuService.save(request);
    }

    @GetMapping("/store/{id}")
    public Page<MenuResponse> getByStoreId(
            @PathVariable("id") Long storeId,
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page, //required = false =-> 꼭 있을필요 없어서.
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size

    ) {
        return menuService.getByStoreId(storeId, PageRequest.of(page, size));
    }
}
