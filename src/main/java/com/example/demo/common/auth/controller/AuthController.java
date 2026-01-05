package com.example.demo.common.auth.controller;

import com.example.demo.common.auth.dto.response.ApiResponse;
import com.example.demo.common.auth.dto.request.AuthLoginRequestDto;
import com.example.demo.common.auth.dto.response.AuthLoginResponseDto;
import com.example.demo.common.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    /**
     * 로그인 API
     */
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthLoginResponseDto>>  loginApi(
            @RequestBody AuthLoginRequestDto requestDto
    ) {
        //데이터준비
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        //비지니스 로직
        AuthLoginResponseDto responseDto = authService.login(requestDto);

        ApiResponse<AuthLoginResponseDto> apiResponse = new ApiResponse<>("success", 200, responseDto);
        ResponseEntity<ApiResponse<AuthLoginResponseDto>> response = new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        return response;
    }
}
