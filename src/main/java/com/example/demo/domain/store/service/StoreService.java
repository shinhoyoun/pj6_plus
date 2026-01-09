package com.example.demo.domain.store.service;

import com.example.demo.common.util.cache_v2.SearchKeywordCacheManager;
import com.example.demo.domain.search.service.CacheService;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    // 인기검색어 TOP 10
    private static final int POPULAR_KEYWORD_LIMIT = 5;

    private final StoreRepository storeRepository;
    private final CacheService cacheService;

    // 인기 검색어 저장소
    private final ConcurrentHashMap<String, LongAdder> keywordCountMap = new ConcurrentHashMap<>();


    // 인기 검색어 조회
    public List<String> getPopularMainItems() {

        log.info("Store service - 인기검색어 TOP {} 조회 ", POPULAR_KEYWORD_LIMIT);
        return cacheService.getTopKeywords(POPULAR_KEYWORD_LIMIT);
    }


    // v2 ------------------

    public Page<Store> search(String keyword, Pageable pageable) {

        cacheService.increaseKeywordRanking(keyword);

        return storeRepository.findByMainItem(keyword, pageable);
    }

    public Page<Store> searchV2(String keyword, Pageable pageable) {

        // 검색어 기준 랭킹 증가
        cacheService.increaseKeywordRanking(keyword);

        // mainItem 기준 LIKE 검색
        return storeRepository.findByMainItem(keyword, pageable);
    }


    // 인기검색어 TOP 5 조회
    public List<String> getPopularKeywords() {

        return cacheService.getTopKeywords(POPULAR_KEYWORD_LIMIT);
    }

}
