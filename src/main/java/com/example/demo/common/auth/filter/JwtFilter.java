//package com.example.demo.common.auth.filter;
//
//import com.example.demo.common.auth.dto.response.UserAuthInfo;
//import com.example.demo.common.auth.service.JwtService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter { //요청 1번당 JWT 검사도 1번
//
//    private final JwtService jwtService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//
//        //JWT 검증이 필요 없는 경우
//        //1.데이터 불러오기
//        String requestURI = request.getRequestURI();
//        String method= request.getMethod();
//
//        //2. 검사 없이 불러오는 로직
//        //회원가입 API도 통과할 수 있게 작성 -> //포스트 메서드 .equals 작성
//        if (method.equals("POST") && (requestURI.equals("/api/auth/login") || requestURI.equals("/api/users"))){ //.equals("/api/users"
//            filterChain.doFilter(request,response); //필터 통과
//            return;
//        }
//
//        //JWT 토큰이 여부 검사
//        String authorizationHeader = request.getHeader("Authorization");
//        //1. 토큰 없는 경우
//        if (authorizationHeader == null || authorizationHeader.isBlank()) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt 토큰이 필요합니다.");
//            return;
//        }
//
//        if (!authorizationHeader.startsWith("Bearer ")) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Bearer 형식이어야 합니다.");
//            return;
//        }
//
//        //2. 토큰이 있는 경우
//        String jwt = authorizationHeader.substring(7);
//
//        //JWT 토큰이 유효하지 않은 경우 (차단)
//        try{
//            System.out.println("JWT = " + jwt);
//            UserAuthInfo authInfo = jwtService.validateToken(jwt);
//            Long loginUserId = authInfo.getUserId();
//
//            System.out.println("Authorization = " + authorizationHeader);
//
//            request.setAttribute("loginUserId", loginUserId);
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(loginUserId, null, List.of());
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//
//            filterChain.doFilter(request, response);
//            return;
//
//        } catch (RuntimeException e) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"유효하지 않은 토큰입니다.");
//            return;
//        }
//
//        //JWT 토큰에서 북호화 한 데이터 저장
//        Long userId = jwtService.extractUserId(jwt); //유저 ID
//        String username = jwtService.extractUsername(jwt);
//      String auth =  jwtService.extractRole(jwt); //권한 사용 안하면 삭제 예정
//
//        //인증 객체 생성 -> SecurityContext에 저장
//
//
//    }
//}
