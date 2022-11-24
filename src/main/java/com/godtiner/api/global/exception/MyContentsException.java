package com.godtiner.api.global.exception;

public class MyContentsException extends BaseException{

    private BaseExceptionType exceptionType;

    public MyContentsException(BaseExceptionType exceptionType){
        this.exceptionType =exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
