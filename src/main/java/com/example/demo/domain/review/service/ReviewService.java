package com.example.demo.domain.review.service;

//import com.example.demo.common.auth.filter.JwtFilter;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.demo.domain.review.dto.response.ReviewCreateResponseDto;
import com.example.demo.domain.review.dto.response.ReviewGetListResponseDto;
import com.example.demo.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.repository.ReviewRepository;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
//    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;

    /**
     * 생성 기능
     *
     * @param requestDto
     * @return
     */
    @Transactional
    public ReviewCreateResponseDto createReview(Long userId ,ReviewCreateRequestDto requestDto) {


        //1. 스토어 아이디 조회
//        Store store = reviewRepository.findByIdAndIsDeletedFalse(storeId)
//                .orElseThrow(() -> new RuntimeException("스토아 조회할 수 없습니다."));

        //2. 유저 아이디 조회
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("유저 조회할 수 없습니다."));


        //3.데이터 불러오기
        String name = requestDto.getName();
        String content = requestDto.getContent();

        //4. 엔티티생성
        Review review = new Review(name,content);
        //5. 저장
        Review save = reviewRepository.save(review);

        //6.Dto
        Long savedReview = save.getReviewId();
        ReviewCreateResponseDto responseDto = new ReviewCreateResponseDto(
                review.getReviewId(),
                review.getUser().getId(),
                review.getStore().getId(),
                review.getContent(),
                review.getName(),
                review.getCreatedAt()
                );
        return responseDto;

    }

    /**
     * 다 건 조회
     */
    @Transactional
    public ReviewGetListResponseDto foundAll() {

        //멤버 찾기 -> 삭제 데이터 제외
        List<Review> foundAllGet = reviewRepository.findAllByAndIsDeletedFalse();

        //데이터 반영

        //내부
        List<ReviewGetListResponseDto.ReviewGetAllResponseDto> reviewList = new ArrayList<>();
        for (Review review : foundAllGet) {


            ReviewGetListResponseDto.ReviewGetAllResponseDto reviewAllList =
                    new ReviewGetListResponseDto.ReviewGetAllResponseDto(
                            review.getReviewId(),
                            review.getUser().getId(),
                            review.getStore().getId(),
                            review.getContent(),
                            review.getName(),
                            review.getCreatedAt()
                            );

            //저장
            reviewList.add(reviewAllList);
        }

        ReviewGetListResponseDto reviewGetListResponseDto =
                new ReviewGetListResponseDto(reviewList);

        return reviewGetListResponseDto;

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
        //리뷰 아이디 조회
        Review foundReview = reviewRepository.findByReviewIdAndIsDeletedFalse(reviewId)
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
     *
     * @return
     */
    public CommonResponse<Void> deletedReview(Long reviewId, Long longUserId) {
        //리뷰 아이디 조회
        Review foundReview = reviewRepository.findByReviewIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new RuntimeException("댓글을 조회할 수 없습니다."));

        //삭제 권한 -> 유저가 다른 경우
        if(!foundReview.getUser().getId().equals(longUserId)){
            throw new RuntimeException("권한이 없습니다.");
        }

        return new CommonResponse<>( true, "리뷰가 삭제 됐습니다.", null);
    }
}
