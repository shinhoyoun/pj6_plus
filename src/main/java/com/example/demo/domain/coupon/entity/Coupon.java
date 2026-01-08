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
    @JoinColumn(name = "stores_id", nullable = false)
    private Store store;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "issued_coupon_count")
    private long issuedCouponCount;

    @Column(name = "total_coupon_count")
    private final long totalCouponCount = 100;

    public Coupon(Store store, String couponName) {
        this.store = store;
        this.couponName = couponName;
    }

    /**동시성 테스트용 쿠폰객체
     * 테스트 종료
    public Coupon(String couponName) {
        this.couponName = couponName;
    }*/

    public void issuedCoupon() {
        this.issuedCouponCount++;
    }

    public void notIssuedCoupon() {
        if(this.issuedCouponCount > this.totalCouponCount) {
            throw new RuntimeException("더이상 발급되지 않는 쿠폰입니다");
        }
    }
}
