package com.example.menu.repository;

import com.example.menu.domain.entity.Menu;
import com.example.menu.domain.response.MenuResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    //jpql
    @Query("select " +
            "new" +
            "com.example.menu.domain.response" +
            ".MenuResponse(m.id, m.name, m.price) " +
            "from  Menu m " +
            "join m.store s " +
            "where m.store.id = :storeId ")
    Page<MenuResponse> findByStore(Long storeId, PageRequest request); //스토어로 찾아와
}
