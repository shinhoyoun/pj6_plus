package com.example.demo.domain.coupon.dto.response;

import com.example.demo.domain.coupon.entity.Coupon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetAllCouponResponseDto {

    private final Long id;
    private final Long storeId;
    private final String couponName;

    public static GetAllCouponResponseDto from(Coupon coupon) {
        return new GetAllCouponResponseDto(coupon.getId(), coupon.getStore().getId(), coupon.getCouponName());
    }
}
