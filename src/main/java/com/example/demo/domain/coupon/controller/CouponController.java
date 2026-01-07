package com.example.demo.domain.coupon.controller;

import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.coupon.dto.response.GetAllCouponResponseDto;
import com.example.demo.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.common.enums.SuccessMessage.GET_COUPON_SUCCESS;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<GetAllCouponResponseDto>>> getAllCoupon() {
        List<GetAllCouponResponseDto> result = couponService.getAllCoupon();
        return ResponseEntity.ok(GlobalResponse.success(GET_COUPON_SUCCESS, result));
    }
}
