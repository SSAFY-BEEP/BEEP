package com.example.Beep.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    /*
     * 401 UNAUTHORIZED: 권한 없음
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "정보를 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /*
     * 406 NOT_ACCEPTABLE: 해당 유저토큰의 데이터와 요청이 불일치 할 경우
     */
    METHOD_NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "해당 유저에게 불가능한 요청입니다."),

    /*
     204 NO_CONTENT: 데이터 조회 결과가 없음
    */
    METHOD_NO_CONTENT(HttpStatus.NO_CONTENT, "해당하는 데이터가 없습니다."),

    /*
     208 ALREADY_REPORTED: 이미 등록된 데이터
     */
    METHOD_ALREADY_REPORTED(HttpStatus.ALREADY_REPORTED, "이미 등록된 데이터입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    ;

    private final HttpStatus status;
    private final String message;

}
