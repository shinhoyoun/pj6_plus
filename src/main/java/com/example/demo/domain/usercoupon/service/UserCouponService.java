package com.example.demo.domain.usercoupon.service;

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

@Service
@RequiredArgsConstructor
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Transactional
    public IssuedUserCouponResponseDto issuedCouponWithLock(long couponId, long userId) {
        Coupon foundCoupon = couponRepository.findByIdForLOCK(couponId).orElseThrow(
                () -> new RuntimeException("존재하지 않은 쿠폰입니다.")
        );

        User foundUser = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("존재하지 않은 유저입니다.")
        );

        boolean exists = userCouponRepository.existsByCouponIdAndUserId(couponId, userId);

        if(exists) {
            throw new RuntimeException("중복 발급이 불가합니다.");
        }

        UserCoupon userCoupon = new UserCoupon(foundCoupon, foundUser);

        UserCoupon newUserCoupon = userCouponRepository.save(userCoupon);

        foundCoupon.issuedCoupon();

        foundCoupon.notIssuedCoupon();

        long userCouponId = newUserCoupon.getId();

        return new IssuedUserCouponResponseDto(userCouponId);
    }
}
