package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.dto.StoreListResponse;
import com.example.demo.domain.store.dto.response.ApiResponse;
import com.example.demo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public ResponseEntity<ApiResponse<StoreListResponse>> getStoresApi(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) String status
    ) {
        StoreListResponse response = storeService.getStores(totalRating, status);
        ApiResponse<StoreListResponse> apiResponse = new ApiResponse("전체평가 및 업소상태 필터조회", response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }



}
