package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReservationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationApplyService {

    // 날짜별 예약정보 확인 (매장 관리자 전용)
    List<ReservationDto.Response> getReservationByDate(Long storeId, String specificDate);

    // 예약 승인/거절 (매장 관리자 전용)
    ResponseEntity<String> processReservation(Long reservationId);
  }
