package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {


    private final StoreService storeService;

    // v2
    @GetMapping("/search")
    public Page<Store> search(
            @RequestParam String keyword,
            @RequestParam Long userId,
            Pageable pageable
    ) {
        return storeService.search(keyword, userId, pageable);
    }

    @GetMapping("/popular")
    public List<String> popular(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return storeService.getPopularKeywords(limit);
    }

}
