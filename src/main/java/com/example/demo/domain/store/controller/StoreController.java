package com.example.demo.domain.store.controller;

import com.example.demo.domain.search.service.CacheService;
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
    private final CacheService cacheService;

    // v1
    // 검색
    @GetMapping("/search")
    public Page<Store> search(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return storeService.search(keyword, pageable);
    }

    // v2

    @GetMapping("/searchs")
    public Page<Store> searchRedis(
            @RequestParam("keyword") String keyword,
            Pageable pageable
    ) {
        return storeService.searchV2(keyword, pageable);
    }


    // 인기검색어 TOP N
    @GetMapping("/popular")
    public List<String> popular(String keyword) {
        return storeService.getPopularKeywords();
    }

}
