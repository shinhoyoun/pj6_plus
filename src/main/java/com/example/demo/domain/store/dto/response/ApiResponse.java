package com.example.demo.domain.store.dto.response;

import com.example.demo.domain.store.dto.StoreListResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final String message;
    private final T data;

}
