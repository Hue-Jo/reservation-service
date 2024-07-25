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
     * 예약신청 _ 예약번호 반환
     */
    @PostMapping("/request")
    public ResponseEntity<?> requestReservation(@RequestBody @Valid ReservationDto.Request reservationDto) {
        Long reservationId = reservationService.requestReservation(reservationDto);
        String message = String.format("예약이 완료되었습니다. 예약번호는 %d입니다." +
                " 승인이 완료되면 입력해주신 이메일로 확인메일을 보내드립니다.", reservationId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(message);
    }


    /**
     * 예약정보 확인 (예약번호 입력시)
     */
    @GetMapping("/confirm/{reservationId}")
    public ResponseEntity<?> confirmReservation(@PathVariable Long reservationId) {
        Optional<ReservationDto.Request> reservationInfo = reservationService.confirmReservation(reservationId);
        return reservationInfo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    /**
     * 예약시간 수정 (예약번호 입력시)
     */
    @PatchMapping("/update/{reservationId}")
    public ResponseEntity<String> updateReservation(@PathVariable Long reservationId,
                                                    @RequestBody ReservationDto.UpdateDt updateDt) {
        return reservationService.updateReservation(reservationId, updateDt);
    }


    /**
     * 예약취소 (예약번호 입력시)
     */
    @DeleteMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }


    /**
     * 방문확인
     * 10분 이상 지각시, 30분 후 이용가능
     * 미뤄진 예약 시간에 이미 예약자가 있는 경우, 예약을 새롭게 해야한다는 메시지 반환
     */
    @PostMapping("/visit/{reservationId}")
    public ResponseEntity<String> visitConfirm(@PathVariable Long reservationId) {
        String message = reservationService.confirmVisit(reservationId);
        return ResponseEntity.ok(message);

    }

    /**
     * 날짜별 예약정보 확인 (MANAGER ONLY)
     */
    @GetMapping("/check-schedule/{date}")
    public void checkReservationSchedule(@PathVariable LocalDate date) {

    }
}
