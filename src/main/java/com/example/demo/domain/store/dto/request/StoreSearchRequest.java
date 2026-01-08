package com.example.demo.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class StoreSearchRequest {

    private Integer totalRating;
    private String status;
}
