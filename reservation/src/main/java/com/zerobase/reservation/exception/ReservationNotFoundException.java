package com.zerobase.reservation.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String s) {
        super(s);
    }
}
