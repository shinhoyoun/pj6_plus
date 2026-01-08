package com.example.demo.domain.usercoupon.repository;

import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.usercoupon.entitiy.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository <UserCoupon, Long> {
    boolean existsByCouponIdAndUserId(long couponId, long userId);


    Optional<UserCoupon> findByCouponAndUser(Coupon coupon, User user);
}
