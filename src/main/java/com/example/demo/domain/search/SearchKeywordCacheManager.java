<<<<<<<< HEAD:src/main/java/com/example/demo/domain/search/SearchKeywordCacheManager.java
package com.example.demo.domain.search;
========
package com.example.demo.common.util.cache_v2;
>>>>>>>> dev:src/main/java/com/example/demo/common/util/cache_v2/SearchKeywordCacheManager.java

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchKeywordCacheManager {

    private final CacheManager cacheManager;

    // 조회수 증가 (어뷰징 방지 포함)
    public void increaseCount(String keyword, Long userId) {

        if (keyword == null || keyword.isBlank() || userId == null) {
            return;
        }

        // spring cache
        Cache countCache = cacheManager.getCache("searchCountCache");
        Cache abuseCache = cacheManager.getCache("searchAbuseCache");

        String abuseKey = keyword + ":" + userId;

        // 어뷰징 체크
        Integer abused = abuseCache.get(abuseKey, Integer.class);
        if (abused != null) {
            return; // 이미 조회한 사용자는 카운트 안함
        }

        // 조회수 카운트
        Integer currentCount = countCache.get(keyword, Integer.class);
        countCache.put(keyword, currentCount == null ? 1 : currentCount + 1);

        log.info("SearchKeywordCacheManager - keyword = {} , count = {}", keyword, currentCount);

        // 어뷰징 기록
        abuseCache.put(abuseKey, 1);

        log.info("검색어 조회수 증가 keyword = {} , userId = {}", keyword, userId);

    }


    /**
     * 인기 검색어 TOP N 조회
     */
    public List<String> getTopKeywords(int limit) {

        // CaffeineCache로 캐스팅 (여기서만)
        CaffeineCache caffeineCache =
                (CaffeineCache) cacheManager.getCache("searchCountCache");

        // Native Caffeine Cache
        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                caffeineCache.getNativeCache();

        return nativeCache.asMap().entrySet().stream()
                .sorted((a, b) ->
                        ((Integer) b.getValue()).compareTo((Integer) a.getValue()))
                .limit(limit)
                .map(e -> (String) e.getKey())
                .toList();
    }

}
