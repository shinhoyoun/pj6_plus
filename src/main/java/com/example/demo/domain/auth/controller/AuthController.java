package com.example.demo.domain.auth.controller;

import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.auth.dto.request.AuthLoginRequestDto;
import com.example.demo.domain.auth.dto.response.AuthLoginResponseDto;
import com.example.demo.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.common.enums.SuccessMessage.AUTH_LOGIN_SUCCESS;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<AuthLoginResponseDto>> loginApi(@RequestBody AuthLoginRequestDto requestDto) {
        AuthLoginResponseDto result = authService.login(requestDto);
        return ResponseEntity.ok(GlobalResponse.success(AUTH_LOGIN_SUCCESS,result));
    }
}
