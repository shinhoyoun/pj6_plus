package com.example.demo.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching // 캐시 기능 활성화 -> 없으면 캐시 활성화 안됨
public class CacheConfig {

    // v1
    @Bean
    public Cache<String, Boolean> keywordCache() {

        return Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(Duration.ofDays(1))
                .build();
    }


    // v2
//    @Bean
//    public CacheManager cacheManger() {
//
//        CaffeineCacheManager manager = new CaffeineCacheManager(
//                "searchCountCache",     // 키워드별 조회수
//                            "searchAbuseCache",     // 어뷰징 방지
//                            "searchRankCache"       // 인기검색어 랭킹
//        );
//
//        manager.setCaffeine(
//                Caffeine.newBuilder()
//                        .maximumSize(10_000)
//                        .expireAfterWrite(Duration.ofDays(1)) // 하루유지 (자정 리셋)
//        );
//
//        return manager;
//    }


}