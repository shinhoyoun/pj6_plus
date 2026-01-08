package com.example.demo.domain.search.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CacheService {

    private final Cache keywordCache;
    private final CacheManager cacheManager;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String RANKING_KEY = "popular:keywords";   // 오늘의 랭킹이라면 요청 들어온 날짜를 키값에 붙여야한다.



    // v1 ---------------------------

    public CacheService(CacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {

        this.cacheManager = cacheManager;
        this.keywordCache = cacheManager.getCache("keyword");

        if (this.keywordCache == null) {
            throw new IllegalStateException("cache 'keyword' not found");
        }
        this.redisTemplate = redisTemplate;
    }

    // 캐싱 저장
    public void saveKeyword(String keyword) {

        log.info("키워드 저장 시도:{}", keyword);

        if (this.keywordCache == null) return;

        keywordCache.put(keyword, keyword);

        CaffeineCache caffeineCache = (CaffeineCache) keywordCache;
        Map<Object, Object> cacheMap = caffeineCache.getNativeCache().asMap();

        log.info("현재 저장된 캐시 전체 목록={}", cacheMap);
    }

    // 단건 조회
    public String GetOneKeyword(String keyword) {

        return keywordCache.get(keyword, String.class);
    }

    // 전체 조회
    public Map<Object, Object> getAllKeyword() {

        Cache cache = cacheManager.getCache("keyword");

        CaffeineCache caffeineCache = (CaffeineCache) cache;

        return caffeineCache.getNativeCache().asMap();
    }

    // v2-------------------------------------------

    // 오늘 날짜 기준 키 생성
    private String getTodayRankingKey() {

        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return RANKING_KEY + today;
    }

    // Z-Set 랭킹 증가
    public void increaseKeywordRanking(String keyword) {

        String key = getTodayRankingKey();

        redisTemplate.opsForZSet().incrementScore(RANKING_KEY, keyword, 1);

        // TTL : 2일로 설정
        redisTemplate.expire(key, 48L, TimeUnit.HOURS);

        log.info("Z-SET 조회수 증가- key={}, keyword={}", key, keyword);
    }


    // 인기검색어 TOP N 조회
    public List<String> getTopKeywords(int limit) {

        String key = getTodayRankingKey();

        // 어떻게 반환되는지 확인하고 가공해라
        return redisTemplate.opsForZSet()
                .reverseRange(RANKING_KEY, 0, limit - 1)
                .stream()
                .map(Object::toString)
                .toList();
    }


}

