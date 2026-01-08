package com.example.demo.domain.usercoupon.service;

import com.example.demo.common.annotation.RedisLock;
import com.example.demo.common.exception.CustomException;
import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.coupon.repository.CouponRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.usercoupon.dto.response.IssuedUserCouponResponseDto;
import com.example.demo.domain.usercoupon.entitiy.UserCoupon;
import com.example.demo.domain.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.common.enums.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    // aop 적용전 적용후로 나눠 테스트코드 작성
    @Transactional
    @RedisLock(key = "lock:coupon")
    public IssuedUserCouponResponseDto issuedCouponWithLock(long couponId, long userId) {
        Coupon foundCoupon = couponRepository.findByIdForLOCK(couponId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COUPON)
        );

        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        boolean exists = userCouponRepository.existsByCouponIdAndUserId(couponId, userId);

        if(exists) {
            throw new CustomException(ALREADY_ISSUED_COUPON);
        }

        UserCoupon userCoupon = new UserCoupon(foundCoupon, foundUser);

        UserCoupon newUserCoupon = userCouponRepository.save(userCoupon);

        foundCoupon.issuedCoupon();

        foundCoupon.notIssuedCoupon();

        long userCouponId = newUserCoupon.getId();

        return new IssuedUserCouponResponseDto(userCouponId);
    }
}
