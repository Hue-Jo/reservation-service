package com.zerobase.reservation.exception;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String s) {
        super(s);
    }
}
