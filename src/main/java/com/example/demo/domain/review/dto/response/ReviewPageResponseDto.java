package com.example.demo.domain.review.dto.response;

import com.example.demo.domain.review.dto.dto.ReviewDto;
import com.example.demo.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewPageResponseDto {
    private final String content;

    public static ReviewPageResponseDto from(Review review) {
        return new ReviewPageResponseDto(
                review.getContent()
        );
    }

}
