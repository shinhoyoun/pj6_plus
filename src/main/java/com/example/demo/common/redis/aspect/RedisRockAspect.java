package com.example.demo.common.redis.aspect;

import com.example.demo.common.annotation.RedisLock;
import com.example.demo.common.enums.ErrorMessage;
import com.example.demo.common.exception.CustomException;
import com.example.demo.common.redis.service.RedisLockService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.example.demo.common.enums.ErrorMessage.FAILED_LOCK;

@Aspect
@Component
@RequiredArgsConstructor
public class RedisRockAspect {
    private final RedisLockService redisLockService;

    @Around("@annotation(redisLock)")
    public Object run(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {

        String keyPreFix = redisLock.key();

        String value = UUID.randomUUID().toString();

        Object[] args = joinPoint.getArgs();
        Object objectId = args[0];

        String key = keyPreFix + objectId;

        if (!redisLockService.tryLock(key, value, redisLock.timeout())) {
            throw new CustomException(FAILED_LOCK);
        }

        try {
            return joinPoint.proceed();
        } finally {
            redisLockService.unlock(key, value);
        }
    }
}
