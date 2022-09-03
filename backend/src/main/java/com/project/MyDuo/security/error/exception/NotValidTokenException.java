package com.project.MyDuo.security.error.exception;

public class NotValidTokenException extends BusinessException {
    public NotValidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
