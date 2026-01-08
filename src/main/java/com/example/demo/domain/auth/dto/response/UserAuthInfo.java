package com.example.demo.domain.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAuthInfo {

    private final Long userId;
    private final String userEmail;
}
