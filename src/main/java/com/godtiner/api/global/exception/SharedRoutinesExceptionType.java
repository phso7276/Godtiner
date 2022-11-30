package com.godtiner.api.global.exception;

import org.springframework.http.HttpStatus;

public enum SharedRoutinesExceptionType implements BaseExceptionType{
    SHARED_ROUTINES_NOT_FOUND(700, HttpStatus.NOT_FOUND, "찾으시는 포스트가 없습니다"),
    NOT_AUTHORITY_UPDATE_SHARED_ROUTINES(701, HttpStatus.FORBIDDEN, "포스트를 업데이트할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_SHARED_ROUTINES(702, HttpStatus.FORBIDDEN, "포스트를 삭제할 권한이 없습니다.");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    SharedRoutinesExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
