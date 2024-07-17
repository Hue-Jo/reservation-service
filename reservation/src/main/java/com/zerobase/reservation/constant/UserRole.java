package com.zerobase.reservation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    USER("이용자"),
    MANAGER("관리자");


    private final String description;
}
