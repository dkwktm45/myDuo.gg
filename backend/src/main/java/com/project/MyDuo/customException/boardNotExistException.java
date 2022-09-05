package com.project.MyDuo.customException;

public class boardNotExistException extends RuntimeException{
    public boardNotExistException(String message) {
        super(message);
    }
}
