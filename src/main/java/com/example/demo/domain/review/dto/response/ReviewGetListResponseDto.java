package com.example.demo.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewGetListResponseDto {
//    private final String content;
    private final List<ReviewGetAllResponseDto> reviewList;

    public static class ReviewGetAllResponseDto {
        private final Long id;
        private final Long userId;
        private final Long stores;
        private final String content;
        private final String name;
        private final LocalDateTime createdAt;

        public ReviewGetAllResponseDto(Long id, Long userId, Long stores, String content, String name, LocalDateTime createdAt) {
            this.id = id;
            this.userId = userId;
            this.stores = stores;
            this.content = content;
            this.name = name;
            this.createdAt = createdAt;
        }
    }
}
