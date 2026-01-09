package com.example.demo.domain.review.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.demo.domain.review.dto.response.GetAllReviewResponseDto;
import com.example.demo.domain.review.dto.response.ReviewCreateResponseDto;
import com.example.demo.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.repository.ReviewRepository;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.common.enums.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewCreateResponseDto createReview(Long userId, Long storeId, ReviewCreateRequestDto requestDto) {

        Store foundStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_STORE));

        User foundUser = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Review newReview = new Review(
                foundStore,
                foundUser,
                requestDto.getContent(),
                requestDto.getName()
        );

        Review saveReview = reviewRepository.save(newReview);

        return new ReviewCreateResponseDto(
                saveReview.getId(),
                saveReview.getStore(),
                saveReview.getUser(),
                saveReview.getContent(),
                saveReview.getName(),
                saveReview.getCreatedAt()
        );

    }

    @Transactional(readOnly = true)
    public List<GetAllReviewResponseDto> foundAll() {

//        List<Review> foundAllReview = reviewRepository.findAllAndIsDeletedFalse();
        List<Review> foundAllReview = reviewRepository.findAllByIsDeletedFalse();

       List<GetAllReviewResponseDto> reviewList = foundAllReview.stream()
               .map(GetAllReviewResponseDto :: from)
               .toList();

       return reviewList;
    }

    @Transactional
    public ReviewUpdateResponseDto updateReview(Long reviewId, ReviewCreateRequestDto requestDto) {

        Review review = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_REVIEW));

        review.update(requestDto.getContent());

        return ReviewUpdateResponseDto.from(review);
    }

    @Transactional
    public void deletedReview(Long reviewId) {
        Review foundReview = reviewRepository.findByIdAndIsDeletedFalse(reviewId).orElseThrow(
                () -> new CustomException(NOT_FOUND_REVIEW)
        );

        foundReview.softDelete();
    }
}
