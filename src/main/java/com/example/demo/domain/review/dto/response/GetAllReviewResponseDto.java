package com.example.demo.domain.review.dto.response;

import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetAllReviewResponseDto {

    private final Long id;
    private final User user;
    private final Store store;
    private final String content;
    private final String name;
    private final LocalDateTime createdAt;

    public static GetAllReviewResponseDto from(Review review) {
        return new GetAllReviewResponseDto(
                review.getId(),
                review.getUser(),
                review.getStore(),
                review.getContent(),
                review.getName(),
                review.getCreatedAt()
        );
    }
}
