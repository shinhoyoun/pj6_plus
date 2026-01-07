package com.example.demo.common.auth.dto.response;

import lombok.Getter;

@Getter
public class JwtUserPayload {

    /**
     * 토큰 생성 로직에서 사용
     */
    private Long userId;
    private String userEmail;

    public JwtUserPayload(Long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
