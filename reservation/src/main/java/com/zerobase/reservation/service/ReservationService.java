package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReservationDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ReservationService {

    // 예약신청
    Long requestReservation(ReservationDto.Request reservationDto, String userEmail);

    //예약정보 확인
    ResponseEntity<ReservationDto.Request> confirmReservation(Long reservationId);

    // 예약정보 수정
    ResponseEntity<String> updateReservation(Long reservationId, ReservationDto.UpdateDt updateDt);

    // 예약취소
    ResponseEntity<String> cancelReservation(Long reservationId);

    // 방문확인
    String confirmVisit(Long reservationId);
}
