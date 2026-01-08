package com.example.demo.domain.usercoupon.service;

import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.coupon.repository.CouponRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.usercoupon.entitiy.UserCoupon;
import com.example.demo.domain.usercoupon.repository.UserCouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Slf4j
class UserCouponServiceTest {

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    private Throwable errorMessage;

    @Test
    @DisplayName("유저 200명의 동시성 이슈와 동시성 이슈에 대한 락 적용 후 100명의 유저에게만 쿠폰 발급")
    public void userForLock_issuedOneCouponUsers_oneHundredIssuedCouponTest() throws InterruptedException {
        // give
        // 쿠폰 객체, 테스트 종료 후 store_id null x
        Coupon coupon = new Coupon("text 쿠폰");

        couponRepository.save(coupon);

        // 200명의 유저 객체
        List<User> users = IntStream.rangeClosed(1, 200)
                .mapToObj(i -> userRepository.save(
                        new User("user" + i, "user" + i, "test1111", "test")
                ))
                .toList();

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(200);

        for (User user : users) {
            executorService.execute(() -> {
                try {
                    userCouponService.issuedCouponWithLock(coupon.getId(), user.getId());
                } catch (RuntimeException exception) {
                    throw new RuntimeException(exception.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        Coupon foundCoupon = couponRepository.findById(coupon.getId())
                .orElseThrow(() -> new RuntimeException("coupon not found")
                );
        // 발급된 쿠폰 수
        long issuedCouponCount = foundCoupon.getIssuedCouponCount();

        // 유저 100만 쿠폰 발급
        assertEquals(100, issuedCouponCount);
    }


    @Test
    @DisplayName("유저 101명의 동시성 이슈와 동시성 이슈에 대한 비관적 락 적용 후 101명부터 쿠폰발급을 막는 예외발생")
    public void userForLock_issuedOneCouponUsers_exceptionTest() throws InterruptedException {
        // give
        // 쿠폰 객체, 테스트 종료 후 store_id null x
        Coupon coupon = new Coupon("text 쿠폰");

        couponRepository.save(coupon);

        // 100명의 유저 객체
        List<User> users = IntStream.rangeClosed(1, 101)
                .mapToObj(i -> userRepository.save(
                        new User("user" + i, "user" + i, "test1111", "test")
                ))
                .toList();

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(51);
        CountDownLatch latch = new CountDownLatch(101);

        for (User user : users) {
            executorService.execute(() -> {
                try {
                    userCouponService.issuedCouponWithLock(coupon.getId(), user.getId());
                } catch (RuntimeException exception) {
                    errorMessage = exception;
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        // 101번째 쿠폰 발급 요청 예외 메시지
        String notIssuedCoupon = errorMessage.getMessage();

        assertEquals("더이상 발급되지 않는 쿠폰입니다", notIssuedCoupon);
    }

    @Test
    @DisplayName("두 서버 요청을 받아 래디스락 테스트")
    public void multipleServerRequest_redisRockTest() throws InterruptedException {
        // give
        // 쿠폰 객체, 테스트 종료 후 store_id null x
        Coupon coupon = new Coupon("text 쿠폰");
        couponRepository.save(coupon);

        User user = new User("test", "test@naver", "test1234", "홍길동");
        userRepository.save(user);


        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {

            try {
                userCouponService.issuedCouponWithLock(coupon.getId(), user.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // 각자 다른 uuid를 가진 두 서버
        executor.submit(task);
        executor.submit(task);

        // 동시요청을 위한 요청지연
        Thread.sleep(3000);

        // 쿠폰발급 결과
        UserCoupon result = userCouponRepository.findByCouponAndUser(coupon, user).orElseThrow();

        // 서버하나의 요청만 성공
        log.info("쿠폰 발급 결과 {}" , result.getId());
        assertEquals(1, result.getId());
    }
}