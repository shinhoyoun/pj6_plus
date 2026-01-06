package com.example.demo.domain.review.contorller;

import com.example.demo.common.auth.dto.response.ApiResponse;
import com.example.demo.common.response.CommonResponse;
import com.example.demo.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.demo.domain.review.dto.response.ReviewCreateResponseDto;
import com.example.demo.domain.review.dto.response.ReviewGetListResponseDto;
import com.example.demo.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.demo.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


    //생성
    @PostMapping("/{loginUserId}/{storeId}")
    public ResponseEntity<CommonResponse<ReviewCreateResponseDto>> reviewCreateApi(
            @PathVariable Long storeId,
            @PathVariable ("loginUserId") Long loginUserId, // 삭제 예정
            @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewCreateResponseDto responseDto = reviewService.createReview(storeId ,loginUserId, requestDto);

        CommonResponse<ReviewCreateResponseDto> commonResponse = new CommonResponse<>(true, "리뷰가 생성 됐습니다", responseDto);
        ResponseEntity<CommonResponse<ReviewCreateResponseDto>> response = new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        return response;
    }

    //다 건 조회
    @GetMapping
    public ResponseEntity<CommonResponse<ReviewGetListResponseDto>> GetAllPageApi(

    ) {
        ReviewGetListResponseDto responseDto = reviewService.foundAll();
        CommonResponse<ReviewGetListResponseDto> commonResponse = new CommonResponse<>(true, "리뷰가 생성 됐습니다", responseDto);
        ResponseEntity<CommonResponse<ReviewGetListResponseDto>> response = new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        return response;
    }

    //수정
    @PutMapping("/{reviewId}")
    public  ResponseEntity<CommonResponse<ReviewUpdateResponseDto>> reviewUpdateApi(
            @PathVariable Long reviewId,
            @PathVariable Long loginUserId,
            @RequestBody ReviewCreateRequestDto requestDto
    ) {
        ReviewUpdateResponseDto responseDto = reviewService.updateReview(reviewId, loginUserId, requestDto);

        CommonResponse<ReviewUpdateResponseDto> commonResponse = new CommonResponse<>(true, "리뷰가 생성 됐습니다", responseDto);
        ResponseEntity<CommonResponse<ReviewUpdateResponseDto>> response = new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
        return response;
    }

    //삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<CommonResponse<Void>> reviewDeletedApi(
            @PathVariable Long reviewId,
            @PathVariable Long loginUserId
    ) {
        reviewService.deletedReview(reviewId, loginUserId);

        CommonResponse<Void> apiResponse = new CommonResponse<>(true, "리뷰가 삭제 됐습니다.", null);
        ResponseEntity<CommonResponse<Void>> response = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        return response;
    }

}
