package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReservationDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationApplyService {
    /**
     * 점주 전용 날짜별 예약정보 확인
     */
    List<ReservationDto.Response> getReservationsByStoreAndDate(Long storeId, LocalDate date);

}
