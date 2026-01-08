package com.example.demo.domain.store.controller;

import com.example.demo.common.response.CommonResponse;
import com.example.demo.domain.store.dto.response.StoreListResponse;
import com.example.demo.domain.store.dto.response.StorePageResponse;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {


    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<CommonResponse<StoreListResponse>> getStoresApi(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) String status
    ) {
        StoreListResponse response = storeService.getStores(totalRating, status);
        CommonResponse<StoreListResponse> apiResponse = new CommonResponse(true,"전체평가 및 업소상태 필터조회", response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /**
     *
     */
    @GetMapping("/page")
    public ResponseEntity<CommonResponse<StorePageResponse>> getStoresPageApi(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable page
    ) {
        StorePageResponse storesPage = storeService.getStoresPage(totalRating, status, page);
        CommonResponse<StorePageResponse> apiResponse = new CommonResponse(true,"10개씩 페이징 조회", storesPage);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    // 인기 검색어 Top 10 조회
    @GetMapping("/popular-keywords")
    public List<String> popularKeywords() {

        log.info("Store controller popularKeyword - 도착");
        return storeService.getPopularMainItems();
    }


    // 주요취급품목 검색 (LIKE 검색)
    @GetMapping("/search")
    public Page<Store> search(@RequestParam(required = false) String keyword, Pageable pageable) {

        log.info("Store controller search - 도착");
        return storeService.searchByMainItem(keyword, pageable);
    }

}
