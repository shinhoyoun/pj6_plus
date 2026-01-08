package com.example.demo.domain.review.dto.response;

import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReviewUpdateResponseDto {
    private final Long id;
    private final Store store;
    private final User user;
    private final String content;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;

    public static ReviewUpdateResponseDto from(Review review) {
        return new ReviewUpdateResponseDto(
                review.getId(),
                review.getStore(),
                review.getUser(),
                review.getContent(),
                review.getName(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
