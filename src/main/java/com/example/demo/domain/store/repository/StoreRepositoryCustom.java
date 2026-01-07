package com.example.demo.domain.store.repository;

import com.example.demo.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {

    /**
     * 주요취급품목 인기 검색어 조회
     * - mainItem 기준
     * - 등장 횟수 기준 정렬
     * - 상위 N개(limit) 반환
     */
    List<String> findPopularMainItems(int limit);

    /**
     * 주요취급품목 검색 API (v1)
     * - LIKE 검색
     * - 페이지네이션 적용
     */
    Page<Store> findByMainItem(String keyword, Pageable pageable);

}
