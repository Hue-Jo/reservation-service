package com.zerobase.reservation.exception.error;

public class StoreNotExistException extends RuntimeException {
    public StoreNotExistException(String storeName) {
        super(storeName + "은/는 존재하지 않는 매장입니다.");
    }
}
