package com.example.demo.domain.usercoupon.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.common.redis.service.RedisLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.demo.common.enums.ErrorMessage.CLIENT_CLOSED_REQUEST;
import static com.example.demo.common.enums.ErrorMessage.FAILED_LOCK;

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
            throw new CustomException(FAILED_LOCK);
        }

        try{
            Thread.sleep(3000);
            userCouponService.issuedCouponWithLock(couponId, userId);
        } catch (InterruptedException interruptedException) {
            throw new CustomException(CLIENT_CLOSED_REQUEST);
        } finally{
            redisLockService.unlock(lockKey, uuidStr);
        }
    }
}
