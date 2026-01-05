package com.example.demo.domain.usercoupon.service;

import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.coupon.repository.CouponRepository;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.StoreRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserCouponServiceTest {

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private StoreRepository storeRepository;

    private static Logger log = LoggerFactory.getLogger(UserCouponServiceTest.class);

    @Test
    @DisplayName("Lock 없는 쿠폰 한개의 유저 100명에 동시성 이슈 테스트")
    public void noLock_issuedOneCouponUsers_concurrencyTest() throws InterruptedException {
        // give
        // db에 저장된 storeId = 1
        Store storeId = storeRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("test")
        );

        // 쿠폰 객체
        Coupon coupon = new Coupon(storeId, "text 쿠폰");

        couponRepository.save(coupon);

        // 100명의 유저 객체
        List<User> users = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> userRepository.save(
                        new User("user" + i, "user" + i, "test1111")
                ))
                .toList();

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);

        for (User user : users) {
            executorService.execute(() -> {
                try {
                    userCouponService.issuedCoupon(coupon.getId(), user.getId());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        Coupon foundCoupon = couponRepository.findById(coupon.getId())
                .orElseThrow(() -> new RuntimeException("coupon not found")
                );


        // 발급된 쿠폰 수
        long issuedCouponCount = foundCoupon.getIssuedCouponCount();

        log.info("발급된 쿠폰 수 : {}", issuedCouponCount);

        assertEquals(100, issuedCouponCount);
    }
}