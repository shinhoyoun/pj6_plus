package com.example.demo.domain.review.contorller;

import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.auth.service.JwtService;
import com.example.demo.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.demo.domain.review.dto.response.GetAllReviewResponseDto;
import com.example.demo.domain.review.dto.response.ReviewCreateResponseDto;
import com.example.demo.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.demo.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.enums.SuccessMessage.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtService jwtService;

    // ✅ 토큰 파싱 공용
    private String resolveToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new RuntimeException("Authorization 헤더가 없습니다.");
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Bearer 토큰 형식이 아닙니다.");
        }
        return authorizationHeader.substring(7);
    }

    //생성
    @PostMapping("/{loginUserId}/{storeId}")
    public ResponseEntity<GlobalResponse<ReviewCreateResponseDto>> reviewCreateApi(
            @PathVariable Long storeId,
            @PathVariable ("loginUserId") Long loginUserId,
            @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewCreateResponseDto result = reviewService.createReview(storeId ,loginUserId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(REVIEW_CREATE_SUCCESS, result));
    }

    //다 건 조회
    @GetMapping
    public ResponseEntity<GlobalResponse<List<GetAllReviewResponseDto>>> GetAllPageApi() {
        List<GetAllReviewResponseDto> result= reviewService.foundAll();
        return ResponseEntity.ok(GlobalResponse.success(REVIEW_LIST_SUCCESS, result));
    }

    //수정
    @PutMapping("/{reviewId}")
    public  ResponseEntity<GlobalResponse<ReviewUpdateResponseDto>> reviewUpdateApi(
            @PathVariable Long reviewId,
            @RequestBody ReviewCreateRequestDto requestDto
    ) {
        ReviewUpdateResponseDto result = reviewService.updateReview(reviewId, requestDto);
        return ResponseEntity.ok(GlobalResponse.success(REVIEW_UPDATE_SUCCESS,result));
    }

    //삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<GlobalResponse<Void>> reviewDeletedApi(@PathVariable Long reviewId) {
        reviewService.deletedReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(GlobalResponse.successNodata(REVIEW_DELETE_SUCCESS));
    }
}
