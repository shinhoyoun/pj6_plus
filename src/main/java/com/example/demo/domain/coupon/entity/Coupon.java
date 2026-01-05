package com.example.demo.domain.coupon.entity;

import com.example.demo.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "issued_copon_count")
    private long issuedCouponCount;

    @Column(name = "total_coupon_count")
    private final long totalCouponCount = 100;

    public Coupon(Store store, String couponName) {
        this.store = store;
        this.couponName = couponName;
    }

    public void issuedCoupon() {
        this.issuedCouponCount++;
    }

    public void expiredCoupon() {
        if(this.issuedCouponCount > this.totalCouponCount) {
            throw new RuntimeException("만료된 토큰입니다");
        }
    }
}
