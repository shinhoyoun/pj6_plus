package com.example.demo.domain.store.service;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    // 인기검색어 TOP 10
    private static final int POPULAR_KEYWORD_LIMIT = 10;

    private final StoreRepository storeRepository;


    // 인기 검색어 조회
    public List<String> getPopularMainItems() {

        log.info("Store service - 인기검색어 TOP {} 조회 ", POPULAR_KEYWORD_LIMIT);
        return storeRepository.findPopularMainItems(POPULAR_KEYWORD_LIMIT);
    }


    // 주요취급품목 검색 - v1
    public Page<Store> searchByMainItem(String keyword, Pageable pageable) {
        return storeRepository.findByMainItem(keyword, pageable);
    }
}
