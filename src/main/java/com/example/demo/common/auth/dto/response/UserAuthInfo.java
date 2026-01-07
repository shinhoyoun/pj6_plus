package com.example.demo.common.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAuthInfo {

    private Long userId;
    private String userEmail;

    public UserAuthInfo(Long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
