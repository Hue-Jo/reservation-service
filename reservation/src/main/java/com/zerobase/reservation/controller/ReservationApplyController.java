package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.service.ReservationApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/reservation-apply")
@RequiredArgsConstructor
public class ReservationApplyController {

    private final ReservationApplyService reservationApplyService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");


    /**
     * 날짜별 예약정보 확인 (MANAGER ONLY)
     */
    @GetMapping("/store/{storeId}/date/{specificDate}")
    public ResponseEntity<?> getReservationsByStoreAndDate(
            @PathVariable Long storeId,
            @PathVariable String specificDate) {

        // 문자열을 LocalDate로 변환
        LocalDate date;
        try {
            date = LocalDate.parse(specificDate, DATE_FORMATTER);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("날짜를 'yyyy년 MM월 dd일' 형태로 다시 입력해주세요");
        }

        // 예약 조회 서비스 호출
        List<ReservationDto.Response> reservations = reservationApplyService.getReservationsByStoreAndDate(storeId, date);

        // 예약이 없는 경우
        if (reservations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약된 데이터가 없습니다.");
        }
        // 예약이 있는 경우, 예약 리스트 반환
        return ResponseEntity.ok(reservations);
    }
}
