package com.godtiner.api.global.exception;

public class SharedRoutinesException extends BaseException {

    private BaseExceptionType baseExceptionType;


    public SharedRoutinesException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
