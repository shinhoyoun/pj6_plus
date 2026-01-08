package com.example.demo.domain.usercoupon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Slf4j
public class RedissonLockUserCouponService {

    private final UserCouponService userCouponService;
    private final RedissonClient redissonClient;

    // 포스트맨 테스트와 로깅으로 lock 흐름확인
    public void issuedCouponWithRedissonLock(long couponId, long userId) {

        RLock lock = redissonClient.getFairLock("lock:coupon:" + couponId);

        try {
            boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);

            if (res) {
                log.info("락 획득 성공 - look:coupon:{}, user {}", couponId, userId);
                try {
                    Thread.sleep(3500);
                    userCouponService.issuedCouponWithLock(couponId, userId);
                    log.info("쿠폰 발급 완료 - user {}", userId);
                } finally {
                    lock.unlock();
                    log.info("락 해제 완료 - user {}", userId);
                }
            } else {
                log.warn("락 획득 실패 - look:coupon:{}, user {}", couponId, userId);
                throw new RuntimeException("잠시만 기다려주세요");
            }
        } catch (InterruptedException interruptedException) {
            log.error("인터럽트 발생 {}", interruptedException.getMessage());
            throw new RuntimeException("프로세스를 강제 종료합니다.");
        }
    }
}


