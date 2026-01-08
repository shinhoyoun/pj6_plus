package com.example.demo.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreSearchRequest {

    private Integer totalRating;
    private String status;

}
