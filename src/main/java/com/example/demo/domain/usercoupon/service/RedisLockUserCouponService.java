package com.example.demo.domain.usercoupon.service;

import com.example.demo.common.redis.RedisLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisLockUserCouponService {

    private final RedisLockService redisLockService;
    private final UserCouponService userCouponService;

    public void issuedCouponWithRedisLock(long couponId, long userId) {

        String lockKey = "lock:" + "coupon:" + couponId;

        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        if (!redisLockService.tryLock(lockKey, uuidStr, 5)) {
            throw new RuntimeException("잠시후 다시 시도해주세요");
        }

        try{
            userCouponService.issuedCouponWithLock(couponId, userId);
        } finally {
            redisLockService.unlock(lockKey, uuidStr);
        }
    }
}
