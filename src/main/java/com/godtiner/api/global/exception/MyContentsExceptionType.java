package com.godtiner.api.global.exception;

import org.springframework.http.HttpStatus;

public enum MyContentsExceptionType implements BaseExceptionType{

    CONTENT_CAN_NOT_SAVE(10000,HttpStatus.BAD_REQUEST, "루틴 생성에 실패했습니다."),
    CONTENT_CAN_NOT_DELETE(10001, HttpStatus.BAD_REQUEST, "루티 삭제에 실패했습니다.");




    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MyContentsExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
