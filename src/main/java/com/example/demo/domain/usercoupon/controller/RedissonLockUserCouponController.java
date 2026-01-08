package com.example.demo.domain.usercoupon.controller;

import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.usercoupon.service.RedissonLockUserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.enums.SuccessMessage.ISSUED_COUPON_SUCCESS;

@RestController
@RequestMapping("/api/redissonLock/coupon/")
@RequiredArgsConstructor
public class RedissonLockUserCouponController {

    private final RedissonLockUserCouponService redissonLockUserCouponService;

    @PostMapping("/{couponId}/user")
    public ResponseEntity<GlobalResponse<Void>> issuedCoupon(
            @PathVariable Long couponId,
            @RequestBody Long userId) {

        redissonLockUserCouponService.issuedCouponWithRedissonLock(couponId, userId);
        return ResponseEntity.ok(GlobalResponse.successNodata(ISSUED_COUPON_SUCCESS));
    }
}
