package com.example.demo.domain.store.service;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    private final Cache<String, Boolean> keywordCache;

    // 키워드를 캐시에 저장
    public void saveKeyword(String keyword) {

        log.info("키워드 저장 시도 : {} ", keyword);

        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        keywordCache.put(keyword.trim(), true);
        log.info("현재 저장된 캐시 키 목록 : {}", keywordCache.asMap().keySet());
    }

    // 캐시에 저장된 키워드 목록 조회
    public List<String> getKeywords() {
        return new ArrayList<>(keywordCache.asMap().keySet());
    }

}
