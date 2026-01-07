package com.example.demo.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    // 200 OK
    AUTH_LOGIN_SUCCESS("로그인 성공"),
    AUTH_CHECK_PASSWORD_SUCCESS("비밀번호가 확인되었습니다."),
    ISSUED_COUPON_SUCCESS("쿠폰 발급 성공"),
    GET_COUPON_SUCCESS("쿠폰 목록 조회성공")


    // 201


    ;
    private final String message;
}
