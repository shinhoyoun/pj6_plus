package com.example.demo.common.auth.controller;

import com.example.demo.common.auth.dto.request.LoginRequestDto;
import com.example.demo.common.auth.dto.response.LoginResponseDto;
import com.example.demo.common.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    /**
     * 로그인 API
     */
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto>  loginApi(
            @RequestBody LoginRequestDto requestDto
    ) {
        //데이터준비
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        //비지니스 로직
        LoginResponseDto responseDto = authService.login(requestDto);

        ResponseEntity<LoginResponseDto> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }
}
