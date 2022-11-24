package com.godtiner.api.global.exception;

public class MyRoutinesException extends BaseException{
    private BaseExceptionType baseExceptionType;


    public MyRoutinesException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
