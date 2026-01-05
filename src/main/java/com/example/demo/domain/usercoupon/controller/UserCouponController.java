package com.example.demo.domain.usercoupon.controller;

import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.usercoupon.dto.response.IssuedUserCouponResponseDto;
import com.example.demo.domain.usercoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.enums.SuccessMessage.ISSUED_COUPON_SUCCESS;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class UserCouponController {

    private final UserCouponService userCouponService;

    @PostMapping("/{couponId}/user")
    public ResponseEntity<GlobalResponse<IssuedUserCouponResponseDto>> issuedCoupon(
            @PathVariable long couponId,
            @RequestBody long userId) {

        IssuedUserCouponResponseDto result = userCouponService.issuedCoupon(couponId, userId);
        return ResponseEntity.ok(GlobalResponse.success(ISSUED_COUPON_SUCCESS, result));
    }
}
