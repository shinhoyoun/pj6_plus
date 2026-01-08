package com.example.demo.domain.store.service;

import com.example.demo.domain.store.dto.response.StoreListResponse;
import com.example.demo.domain.store.dto.response.StorePageResponse;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    // 전체평가 필터조회, 업체상태 필터조회 기능
    @Transactional(readOnly = true)
    public StoreListResponse getStores(Integer totalRating, String status) {

        List<Store> storeList = storeRepository.findStores(totalRating, status);
        int counts = storeList.size();
        List<StoreListResponse.StoreDto> storeDtoList = new ArrayList<>();

        for (Store store : storeList) {

            StoreListResponse.StoreDto storeDto = new StoreListResponse.StoreDto(
                    store.getTotalRating(),
                    store.getStatus()
            );
            storeDtoList.add(storeDto);
        }
        return new StoreListResponse(counts, storeDtoList);
    }

    //Page활용한 데이터 조회
    @Transactional(readOnly = true)
    public StorePageResponse getStoresPage(Integer totalRating, String status, Pageable pageable) {

        Page<Store> storePage = storeRepository.findStoresPage(totalRating, status, pageable);

        List<StorePageResponse.StorePageDto> storeDtoList =
                storePage.getContent().stream()
                        .map(store -> new StorePageResponse.StorePageDto(
                                store.getCompanyName(),
                                store.getTotalRating(),
                                store.getStatus()
                        ))
                        .toList();

        return new StorePageResponse(
                storePage.getTotalElements(), // 전체 데이터 개수
                storeDtoList
        );
    }


}
