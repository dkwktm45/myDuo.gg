package com.project.MyDuo.customException;

public class userInvalidException extends RuntimeException{
    public userInvalidException() {}

    public userInvalidException(String message) { super(message);}
}
