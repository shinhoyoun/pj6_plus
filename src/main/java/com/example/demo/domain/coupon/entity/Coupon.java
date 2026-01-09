package com.example.demo.domain.coupon.entity;

import com.example.demo.common.exception.CustomException;
import com.example.demo.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import static com.example.demo.common.enums.ErrorMessage.COUPON_OUT_OF_STOCK;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stores_id", nullable = false)
    private Store store;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "issued_coupon_count")
    private long issuedCouponCount = 0;

    @Column(name = "total_coupon_count")
    private final long totalCouponCount = 100;

    public void issuedCoupon() {
        this.issuedCouponCount++;
    }

    public void notIssuedCoupon() {
        if (this.issuedCouponCount > this.totalCouponCount) {
            throw new CustomException(COUPON_OUT_OF_STOCK);
        }
    }
}
