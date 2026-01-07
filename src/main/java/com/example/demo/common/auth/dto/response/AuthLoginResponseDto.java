package com.example.demo.common.auth.dto.response;

import lombok.Getter;

@Getter
public class AuthLoginResponseDto {

    private final String token;

    public AuthLoginResponseDto(String token) {
        this.token = token;
    }

    public static AuthLoginResponseDto from(String token) {
        return new AuthLoginResponseDto(token);
    }
}
