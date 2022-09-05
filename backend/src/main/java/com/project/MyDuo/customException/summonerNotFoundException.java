package com.project.MyDuo.customException;

public class summonerNotFoundException extends RuntimeException{
    summonerNotFoundException () {}

    public summonerNotFoundException(String message) {
        super(message);
    }
}
