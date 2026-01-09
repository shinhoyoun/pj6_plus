package com.example.demo.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    // 400 Bad Request
    NOT_MATCHES_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    INVALID_STORE_DATA(HttpStatus.BAD_REQUEST, "유효하지 않은 스토어 데이터입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 401 Unauthorized
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    // 403 Forbidden
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_REVIEW_OWNER(HttpStatus.FORBIDDEN, "리뷰 작성자만 수정/삭제할 수 있습니다."),

    // 404 Not Found
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "스토어를 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, "파일이 없습니다."),
    API_CALL_ERROR(HttpStatus.NOT_FOUND, "API 호출 중 오류 발생"),


    // 409 Conflict
    EXISTS_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    EXISTS_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    DUPLICATE_STORE(HttpStatus.CONFLICT, "이미 존재하는 스토어입니다."),
    ALREADY_REVIEWED(HttpStatus.CONFLICT, "이미 리뷰를 작성했습니다."),
    COUPON_OUT_OF_STOCK(HttpStatus.CONFLICT, "쿠폰 재고가 소진되었습니다."),
    ALREADY_ISSUED_COUPON(HttpStatus.CONFLICT, "이미 발급받은 쿠폰입니다."),
    EXISTS_DELETE_REVIEW(HttpStatus.CONFLICT, "이미 삭제된 리뷰입니다"),

    //450
    FAILED_LOCK(HttpStatus.NO_CONTENT, "락 획득 실패"),

    //499 Client Closed Request
    CLIENT_CLOSED_REQUEST(HttpStatus.NO_CONTENT,"프로세스를 강제 종료 합니다."),


    // 500 Internal Server Error
    STORE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "스토어 업로드에 실패했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다.")
    ;


    private final HttpStatus status;
    private final String message;
}
