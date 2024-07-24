package com.zerobase.reservation.exception.error;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String s){
        super(s);
    }
}
