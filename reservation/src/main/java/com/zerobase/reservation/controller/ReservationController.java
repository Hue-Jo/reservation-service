package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약신청
     */
    @PostMapping("/request")
    public void requestReservation(@RequestBody ReservationDto reservationDto) {
        reservationService.requestReservation(reservationDto);
    }


    /**
     * 예약정보 확인
     */
    @GetMapping("/cofirm/{id}")
    public ReservationDto confirmReservation(@PathVariable Long id) {
        return reservationService.confirmReservation(id);

    }


    /**
     * 예약정보 수정
     */
    @PatchMapping("/update/{id}")
    public void updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        reservationService.updateReservation(id, reservationDto);
    }


    /**
     * 예약취소
     */
    @DeleteMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }


}
