package com.example.demo.domain.search.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreSearchResposne {

    private Long storeId;
    private String storeName;
    private String mainItem;

    private long keywordViewCount;
}
