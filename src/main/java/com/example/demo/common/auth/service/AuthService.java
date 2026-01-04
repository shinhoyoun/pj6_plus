package com.example.demo.common.auth.service;

import com.example.demo.common.auth.dto.JwtUserPayload;
import com.example.demo.common.auth.dto.request.LoginRequestDto;
import com.example.demo.common.auth.dto.response.LoginResponseDto;
import com.example.demo.common.auth.repository.AuthRepository;
import com.example.demo.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * 로그인 처리
     *
     * @return
     */
    public LoginResponseDto login(LoginRequestDto requestDto) {
        //1. 데이터준비
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        //2. 회원 조회 - 받은 email로 로 비밀번호 검증
        User foundUser = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다"));

        //3. 비밀번호 검증 - 받아온 비밀번호와 기존 비밀번호 일치화
        String encodedPassword = foundUser.getPassword();
        boolean match = passwordEncoder.matches(password, encodedPassword);
        if(!match){
            throw new RuntimeException("invalid creadentials");
        } // 여기까지가 로그인

        //4. 토큰 만들기
        JwtUserPayload jwtUserPayload = new JwtUserPayload(foundUser.getId(), foundUser.getEmail());
        String encodedJwt = jwtService.createToken(jwtUserPayload);

        //5. responseDto 반환
        LoginResponseDto responseDto = new LoginResponseDto(encodedJwt);
        return responseDto;
    }
}
