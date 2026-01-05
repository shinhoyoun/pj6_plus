package com.example.demo.domain.review.dto.response;

import com.example.demo.domain.review.dto.dto.ReviewDto;
import com.example.demo.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewGetAllResponseDto {

    private final Long id;
    private final Long userId;
    private final Long stores;
    private final String content;
    private final String name;
    private final LocalDateTime createdAt;

    public static ReviewGetAllResponseDto from(Review review) {
        return new ReviewGetAllResponseDto(
                review.getReviewId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getContent(),
                review.getName(),
                review.getCreatedAt()
        );
    }
}
