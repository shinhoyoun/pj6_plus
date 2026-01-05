package com.example.demo.domain.review.service;

import com.example.demo.common.auth.filter.JwtFilter;
import com.example.demo.domain.review.dto.dto.ReviewDto;
import com.example.demo.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.demo.domain.review.dto.response.ReviewCreateResponseDto;
import com.example.demo.domain.review.dto.response.ReviewPageResponseDto;
import com.example.demo.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.repository.ReviewRepository;

import com.example.demo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final JwtFilter jwtFilter;

    /**
     * 생성 기능
     *
     * @param requestDto
     * @return
     */
    @Transactional
    public ReviewCreateResponseDto createReview(ReviewCreateRequestDto requestDto) {
        //유저 아이디
//        User user = reviewRepository.findByIdAndIsDeletedFalse()

        //데이터 불러오기
        String content = requestDto.getContent();

        //엔티티 생성
        Review review = new Review(content);

        //저장하기
        Review saveReview = reviewRepository.save(review);

        ReviewDto reviewDto = ReviewDto.from(review);
    }

    /**
     * 다 건 조회
     */
    @Transactional
    public void foundAll() {

    }

    /**
     * 수정 기능
     * @param reviewId
     * @param longUserId
     * @param requestDto
     * @return
     */
    @Transactional
    public ReviewUpdateResponseDto updateReview(Long reviewId, Long longUserId, ReviewCreateRequestDto requestDto) {
        //댓글 아이디 조회
        Review foundReview = reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new RuntimeException("댓글을 조회할 수 없습니다."));


        // 댓글 사용자 아닌 경우

        if (!foundReview.getUser().getId().equals(longUserId)) {
            throw new RuntimeException("댓글 사용자가 아닙니다.");
        }

        //데이터 불러오기
        String content = requestDto.getContent();
        //수정 메서드
        foundReview.update(content);
        //댓글 저장
        Review savedReview = reviewRepository.save(foundReview);
        return ReviewUpdateResponseDto.from(savedReview);
    }


    /**
     * 삭제 -> softDeleted
     */
}
