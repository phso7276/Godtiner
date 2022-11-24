package com.godtiner.api.global.exception;

import org.springframework.http.HttpStatus;

public enum MyRoutinesExceptionType implements BaseExceptionType{
    MY_ROUTINES_NOT_FOUND(700, HttpStatus.NOT_FOUND, "찾으시는 포스트가 없습니다"),
    NOT_AUTHORITY_UPDATE_MY_ROUTINES(701, HttpStatus.FORBIDDEN, "포스트를 업데이트할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_MY_ROUTINES(702, HttpStatus.FORBIDDEN, "포스트를 삭제할 권한이 없습니다.");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MyRoutinesExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }


    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
