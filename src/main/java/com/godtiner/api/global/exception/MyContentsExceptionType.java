package com.godtiner.api.global.exception;

import org.springframework.http.HttpStatus;

public enum MyContentsExceptionType implements BaseExceptionType{
    CONTENTS_NOT_FOUND(10002,HttpStatus.NOT_FOUND,"찾는 항목이 없습니다."),
    CONTENT_CAN_NOT_SAVE(10000,HttpStatus.BAD_REQUEST, "루틴항목 생성에 실패했습니다."),
    CONTENT_CAN_NOT_DELETE(10001, HttpStatus.BAD_REQUEST, "루틴 항목 삭제에 실패했습니다."),
    NOT_AUTHORITY_DELETE_MY_CONTENTS(10003, HttpStatus.FORBIDDEN, "포스트를 삭제할 권한이 없습니다.");




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
