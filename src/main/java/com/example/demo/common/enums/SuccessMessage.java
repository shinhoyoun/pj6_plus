package com.example.demo.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    // 200 OK
    AUTH_LOGIN_SUCCESS("로그인 성공"),
    AUTH_CHECK_PASSWORD_SUCCESS("비밀번호가 확인되었습니다."),
    GET_COUPON_SUCCESS("쿠폰 목록 조회 성공"),
    STORE_SEARCH_SUCCESS("스토어 검색 성공"),
    STORE_DETAIL_SUCCESS("스토어 상세 조회 성공"),
    REVIEW_UPDATE_SUCCESS("리뷰 수정 성공"),
    REVIEW_DELETE_SUCCESS("리뷰 삭제 성공"),
    REVIEW_LIST_SUCCESS("리뷰 목록 조회 성공"),
    USER_UPDATE_SUCCESS("회원정보 수정 성공"),
    USER_DELETE_SUCCESS("회원탈퇴 성공"),
    USER_INFO_SUCCESS("회원정보 조회 성공"),
    FILE_UPLOAD_SUCCESS("파일 업로드가 시작되었습니다. 처리가 완료되면 로그를 확인하세요"),
    API_UPLOAD_SUCCESS("API 업로드 성공"),

    // 201 Created
    CREATED_SUCCESS("생성 성공"),
    ISSUED_COUPON_SUCCESS("쿠폰 발급 성공"),
    STORE_UPLOAD_SUCCESS("스토어 업로드 성공"),
    REVIEW_CREATE_SUCCESS("리뷰 작성 성공"),
    USER_SIGNUP_SUCCESS("회원가입 성공"),
    STORE_UPLOAD_COMPLETE("상점 리스트 업로드 성공");

    private final String message;
}
