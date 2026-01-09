package com.example.demo.domain.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthLoginResponseDto {

    private final String token;

    public static AuthLoginResponseDto from(String token) {
        return new AuthLoginResponseDto(token);
    }
}
