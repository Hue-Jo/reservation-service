package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약신청
     */
    @PostMapping("/request")
    public ResponseEntity<?> requestReservation(@RequestBody @Valid ReservationDto reservationDto) {
        Long reservationId = reservationService.requestReservation(reservationDto);
        String message = String.format("예약이 완료되었습니다. 예약번호는 %d입니다." +
                " 승인이 완료되면 입력해주신 이메일로 확인메일을 보내드립니다.", reservationId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(message);
    }


    /**
     * 예약정보 확인
     */
    @GetMapping("/confirm/{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId) {
        Optional<ReservationDto> reservationInfo = reservationService.confirmReservation(reservationId);
        return reservationInfo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    /**
     * 예약정보 수정
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable Long reservationId,
                                                    @RequestBody ReservationDto reservationDto) {
        return reservationService.updateReservation(reservationId, reservationDto);
    }


    /**
     * 예약취소
     */
    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }


    /**
     * 날짜별 예약정보 확인 (MANAGER ONLY)
     */
    @GetMapping("/check-schedule/{date}")
    public void checkReservationSchedule(@PathVariable LocalDate date) {

    }


    /**
     * 키오스크를 통한 방문확인 feat.예약 번호
     */
    @GetMapping("/kiosk/{id}")
    public void kioskConfirm() {

    }


}
