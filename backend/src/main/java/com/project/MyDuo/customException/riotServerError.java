package com.project.MyDuo.customException;

public class riotServerError extends RuntimeException{
    riotServerError() {}

    public riotServerError(String message) {
        super(message);
    }
}
