package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.service.ReservationApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation-apply")
@RequiredArgsConstructor
public class ReservationApplyController {

    private final ReservationApplyService reservationApplyService;

    /**
     * 날짜별 예약정보 확인 (MANAGER ONLY)
     */
    @GetMapping("/store/{storeId}/date/{specificDate}")
    public ResponseEntity<?> getReservationsByStoreAndDate(
            @PathVariable Long storeId,
            @PathVariable String specificDate) {


        // 예약 조회 서비스 호출
        List<ReservationDto.Response> reservations = reservationApplyService.getReservationByDate(storeId, specificDate);

        // 예약이 없는 경우
        if (reservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약된 데이터가 없습니다.");
        }
        // 예약이 있는 경우, 예약 리스트 반환
        return ResponseEntity.ok(reservations);
    }


    /**
     * 예약 승인/거절
     */
    @PutMapping("/reservation/{reservationId}/process")
    public ResponseEntity<String> processReservation(@PathVariable Long reservationId) {

        return reservationApplyService.processReservation(reservationId);
    }

}
