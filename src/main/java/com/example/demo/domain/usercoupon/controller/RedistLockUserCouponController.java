package com.example.demo.domain.usercoupon.controller;

import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.usercoupon.service.RedisLockUserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.enums.SuccessMessage.ISSUED_COUPON_SUCCESS;

@RestController
@RequestMapping("/api/redisRock/coupon")
@RequiredArgsConstructor
public class RedistLockUserCouponController {

    private final RedisLockUserCouponService redisLockUserCouponService;

    @PostMapping("/{couponId}/user")
    public ResponseEntity<GlobalResponse<Void>> issuedCoupon(
            @PathVariable Long couponId,
            @RequestBody Long userId) {

        redisLockUserCouponService.issuedCouponWithRedisLock(couponId, userId);
        return ResponseEntity.ok(GlobalResponse.successNodata(ISSUED_COUPON_SUCCESS));
    }
}
