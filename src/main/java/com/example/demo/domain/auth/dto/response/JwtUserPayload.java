package com.example.demo.domain.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtUserPayload {

    private Long userId;
    private String userEmail;
}
