package com.example.demo.domain.coupon.service;

import com.example.demo.domain.coupon.dto.response.GetAllCouponResponseDto;
import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public List<GetAllCouponResponseDto> getAllCoupon() {

        List<Coupon> couponList = couponRepository.findAll();

        List<GetAllCouponResponseDto> getCoupon = couponList.stream()
                .map(GetAllCouponResponseDto :: from)
                .toList();

        return getCoupon;
    }
}
