package com.zerobase.reservation.exception;

public class PasswordNotMatchException extends RuntimeException{

    public PasswordNotMatchException(String s) {
        super(s);
    }
}
