package com.example.demo.domain.store.service;

import com.example.demo.common.response.CommonResponse;
import com.example.demo.domain.cache_v2.SearchKeywordCacheManager;
import com.example.demo.domain.store.dto.response.StoreListResponse;
import com.example.demo.domain.store.dto.response.StorePageResponse;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {


//    private final StoreService storeService;


    // 인기검색어 TOP 10
    private static final int POPULAR_KEYWORD_LIMIT = 10;

    private final StoreRepository storeRepository;
    private final SearchKeywordCacheManager cacheManager;

    // 인기 검색어 저장소
    private final ConcurrentHashMap<String, LongAdder> keywordCountMap = new ConcurrentHashMap<>();


    // 인기 검색어 조회
    public List<String> getPopularMainItems() {

        log.info("Store service - 인기검색어 TOP {} 조회 ", POPULAR_KEYWORD_LIMIT);
        return storeRepository.findPopularMainItems(POPULAR_KEYWORD_LIMIT);
    }


    // 주요취급품목 검색 - v1
    public Page<Store> searchByMainItem(String keyword, Pageable pageable) {
        return storeRepository.findByMainItem(keyword, pageable);
    }

//    //인기 검색어 TOP 10 조회 - 인메모리
//    public List<String> getPopularKeywords() {
//        return keywordCountMap.entrySet()// 1. map에 들어가는 모든 키, 값을 가짐.
//                .stream()//2. 하나씩 처리 위해 스트림으로 반환 (for문 대체)
//                //3. 내림차순 , 많이 검색한 키워드가 우선 순위
//                .sorted((a, b) -> Long.compare(b.getValue().sum(), a.getValue().sum()))
//                //4. 검색수 많은 키워드 상위 10으로 제한
//                .limit(10)
//                .map(Map.Entry::getKey)// 키값을 뽑는다
//                .collect(Collectors.toList());//List<String> 반환
//    }

    //저장된 검색 키워드 조회
    public Map<String, Long> getAllKeywords() {
        return keywordCountMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().sum()
                ));
    }


    public Page<Store> search(String keyword, Long userId, Pageable pageable) {

        // 조회수 증가 + 어뷰징 방지
        cacheManager.increaseCount(keyword, userId);

        log.info("store service search - middle");

        // db Like 검색
        return storeRepository.findByMainItem(keyword, pageable);
    }

    public List<String> getPopularKeywords(int limit) {
        return cacheManager.getTopKeywords(limit);
    }


}
