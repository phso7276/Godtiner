package com.godtiner.api.global.file;

import com.godtiner.api.global.exception.BaseException;
import com.godtiner.api.global.exception.BaseExceptionType;

public class FileException extends BaseException {
    private BaseExceptionType exceptionType;


    public FileException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
