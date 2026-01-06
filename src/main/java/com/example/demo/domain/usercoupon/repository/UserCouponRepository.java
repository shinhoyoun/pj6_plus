package com.example.demo.domain.usercoupon.repository;

import com.example.demo.domain.usercoupon.entitiy.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository <UserCoupon, Long> {
    boolean existsByCouponIdAndUserId(long couponId, long userId);
}
