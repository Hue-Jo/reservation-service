package com.zerobase.reservation.exception.error;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String s) {
        super(s);
    }
}
