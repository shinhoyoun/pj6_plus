package com.example.demo.domain.store.service;


import com.example.demo.domain.store.dto.StoreListResponse;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreListResponse getStores(Integer totalRating, String status) {

        // 조회 시 해당 컬럼에 대한 데이터를 csv -> mysql로 옮기기? 변환하기?


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
}
