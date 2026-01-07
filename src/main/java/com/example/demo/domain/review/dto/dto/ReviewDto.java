package com.example.demo.domain.review.dto.dto;

import com.example.demo.domain.review.entity.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReviewDto {
    private final Long id;
    private final Long userId;
    private final Long stores;
    private final String content;
    private final String name;
    private final LocalDateTime createdAt;

    public static ReviewDto from(Review review) {
        return new ReviewDto(
                review.getReviewId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getContent(),
                review.getName(),
                review.getCreatedAt()
        );
    }

    public String getcontent() {
        return content;
    }
}
