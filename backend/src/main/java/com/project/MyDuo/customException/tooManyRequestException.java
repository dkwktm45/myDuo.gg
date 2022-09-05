package com.project.MyDuo.customException;

public class tooManyRequestException extends RuntimeException{
    public tooManyRequestException() {}

    public tooManyRequestException(String message) {
        super(message);
    }
}
