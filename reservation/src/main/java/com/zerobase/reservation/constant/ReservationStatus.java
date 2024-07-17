package com.zerobase.reservation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {

    APPLIED("예약 신청"),
    CANCELED("예약 취소"),

    APPROVED("예약 승인"),
    DENIED("예약 거절"),

    COMPLETED("방문(이용) 완료");


    private final String description;
}
