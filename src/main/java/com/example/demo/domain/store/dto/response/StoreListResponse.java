package com.example.demo.domain.store.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StoreListResponse {

    private final int count;
    private final List<StoreDto> storeList;

    public static class StoreDto {

        private final Integer totalRating;
        private final String status;


        public StoreDto(Integer totalRating, String status) {
            this.totalRating = totalRating;
            this.status = status;
        }

        public Integer getTotalRating() {
            return totalRating;
        }
        public String getStatus() {
            return status;
        }
    }

}
