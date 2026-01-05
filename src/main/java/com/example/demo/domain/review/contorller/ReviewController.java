package com.example.demo.domain.review.contorller;

import com.example.demo.common.auth.dto.response.ApiResponse;
import com.example.demo.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.demo.domain.review.dto.response.ReviewCreateResponseDto;
import com.example.demo.domain.review.dto.response.ReviewPageResponseDto;
import com.example.demo.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.demo.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewCreateResponseDto>> reviewCreateApi(
            @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewCreateResponseDto responseDto = reviewService.createReview(requestDto);

        ApiResponse<ReviewCreateResponseDto> apiResponse = new ApiResponse<>("리뷰가 생성 됐습니다", 200, responseDto);
        ResponseEntity<ApiResponse<ReviewCreateResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        return response;
    }

    @GetMapping
    public ResponseEntity<Page<ReviewPageResponseDto>> GetAllPageApi(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<ReviewPageResponseDto> result = reviewService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    //수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewUpdateResponseDto>> reviewUpdateApi(
            @PathVariable Long reviewId,
            @PathVariable Long loginUserId,
            @RequestBody ReviewCreateRequestDto requestDto
    ) {
        ReviewUpdateResponseDto responseDto = reviewService.updateReview(reviewId, loginUserId, requestDto);

        ApiResponse<ReviewUpdateResponseDto> apiResponse = new ApiResponse<>("메세지가 수정 됐습니ㄷ", 200, responseDto);
        ResponseEntity<ApiResponse<ReviewUpdateResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        return response;
    }
}
