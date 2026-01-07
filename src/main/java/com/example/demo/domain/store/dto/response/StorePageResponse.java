package com.example.demo.domain.store.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StorePageResponse {

    private final Long count;
    private final List<StorePageDto> storeList;

    public static class StorePageDto {

        private final String companyName;
        private final Integer totalRating;
        private final String status;


        public StorePageDto(String companyName, Integer totalRating, String status) {
            this.companyName = companyName;
            this.totalRating = totalRating;
            this.status = status;
        }

        public String getCompanyName() {
            return companyName;
        }

        public Integer getTotalRating() {
            return totalRating;
        }
        public String getStatus() {
            return status;
        }
    }

}
