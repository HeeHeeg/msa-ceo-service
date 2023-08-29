package com.example.menu.service;

import com.example.menu.domain.request.MenuRequest;
import com.example.menu.domain.response.MenuResponse;
import com.example.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public void save(MenuRequest request) {
        menuRepository.save(request.toEntity());
    }

    //한번에 다 보여주는건 하면 안된다. 몇천개 된다면 필요없는걸 다 가져오는것도 자원낭비다.
    public Page<MenuResponse> getByStoreId(Long storeId, PageRequest request) { //그 가게의 메뉴를 봐야 주문을하니 아이디를 가져와서 보여주도록.
        return menuRepository.findByStore(storeId, request);


    }
}
