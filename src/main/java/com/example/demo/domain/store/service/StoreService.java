package com.example.demo.domain.store.service;


import com.example.demo.domain.store.dto.StoreListResponse;
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

    private final StoreRepository storeRepository;

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

        // 1단계 : 실제 데이터값
        // 2단계 : 전체 데이터 갯수
        // 3단계 : Page 객체로 변환

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
