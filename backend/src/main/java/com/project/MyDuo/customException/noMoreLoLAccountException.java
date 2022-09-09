package com.project.MyDuo.customException;

public class noMoreLoLAccountException extends RuntimeException{
    public noMoreLoLAccountException() {}
    public noMoreLoLAccountException(String message) { super(message);}
}
