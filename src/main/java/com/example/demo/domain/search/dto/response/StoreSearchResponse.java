package com.example.demo.domain.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreSearchResponse {

    private Long storeId;
    private String storeName;
    private String mainItem;

    private long keywordViewCount;
}
