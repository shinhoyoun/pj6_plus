package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.dto.StoreListResponse;
import com.example.demo.domain.store.dto.response.ApiResponse;
import com.example.demo.domain.store.dto.response.StorePageResponse;
import com.example.demo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 전체평가 필터조회, 업체상태 필터조회 기능
     * @param totalRating
     * @param status
     */
    @GetMapping
    public ResponseEntity<ApiResponse<StoreListResponse>> getStoresApi(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) String status
    ) {
        StoreListResponse response = storeService.getStores(totalRating, status);
        ApiResponse<StoreListResponse> apiResponse = new ApiResponse("전체평가 및 업소상태 필터조회", response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /**
     * 전체평가 필터조회, 업체상태 필터조회 + 페이징 기능추가
     */
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<StorePageResponse>> getStoresPageApi(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable page
            ) {
        StorePageResponse storesPage = storeService.getStoresPage(totalRating, status, page);
        ApiResponse<StorePageResponse> apiResponse = new ApiResponse("10개씩 페이징 조회", storesPage);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }



}
