package com.project.MyDuo.customException;

public class summonerAlreadyRegisteredException extends RuntimeException{
    public summonerAlreadyRegisteredException() {}
    public summonerAlreadyRegisteredException(String message) { super(message);}
}
