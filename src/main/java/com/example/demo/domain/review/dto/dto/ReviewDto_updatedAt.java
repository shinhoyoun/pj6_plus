package com.example.demo.domain.review.dto.dto;

import com.example.demo.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDto_updatedAt {
    private final Long id;
    private final Long userId;
    private final Long stores;
    private final String content;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;

//    public static ReviewDto from(Review review) {
//        return new ReviewDto(
//                review.getReviewId(),
//                review.getUser().getId(),
//                review.getStore().getId(),
//                review.getContent(),
//                review.getName(),
//                review.getCreatedAt(),
//                review.getUpdatedAt()
//        );
//    }
}
