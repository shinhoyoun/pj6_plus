package com.example.demo.common.auth.service;

import com.example.demo.common.auth.dto.response.JwtUserPayload;
import com.example.demo.common.auth.dto.response.UserAuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    /**
     * 토큰 생성
     *
     * @parom jwtUserPayload 회원페이로드(id, email)
     * @return jwt 토큰
     */

//    @Value("${jwt.secret}")
    @Value("${jwt.secret.key}") // application.yml에 있는 Key
    private String secret;
    private SecretKey key;

    /**
     * 빈 초기화 메서드
     */
    @PostConstruct
    //어플리케이션 실행 시 가장 먼저 실행하게 하는 어노테이션

    //256 으로 맞추기
    void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * jwt 토큰 생성
     */
    public String createToken(JwtUserPayload jwtUserPayload) {
        //1. 토큰 데이터 준비
        Long userId = jwtUserPayload.getUserId();
        String userEmail = jwtUserPayload.getUserEmail();

        Date now = new Date();
        Long tokenUseTime = 60L * 60 * 1000; //1시간
        Date exp = new Date(now.getTime() + tokenUseTime);

        // 2. 토큰 생성
        String jws = Jwts.builder()
                .issuer("localhost:8080")
                .subject(userId.toString())//유저아이디
                .expiration(exp)//만료시간
                .issuedAt(now)//발급시간
                .claim("email", userEmail)
                .signWith(key, Jwts.SIG.HS256) //타입 정하는 것
                .compact();
        return jws;
    }

    /**
     * 토큰 검증 로직
     */
    public UserAuthInfo validateToken(String encodedToken) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)             //     or a static key used to verify all encountered JWSs
                    .build()
                    .parseSignedClaims(encodedToken); // (4) or parseSignedContent(jwsString)
            Claims claims = jws.getPayload();

            Long userId = Long.valueOf(claims.getSubject());
            String email = claims.get("email", String.class);
            return new UserAuthInfo(userId, email);

        } catch (JwtException ex) {
            throw ex;
        }
    }

    /**
     * 데이터 북호화
     * @param token
     * @return
     */

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).get("username",String.class);
    }

}
