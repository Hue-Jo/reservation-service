package com.zerobase.reservation.controller;

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
    public void requestReservation() {

    }


    /**
     * 예약정보 확인
     */
    @GetMapping("/cofirm")
    public void confirmReservation() {

    }


    /**
     * 예약정보 수정
     */
    @PatchMapping("/update")
    public void updateReservation() {

    }


    /**
     * 예약취소
     */
    @DeleteMapping("/cancel")
    public void cancelReservation() {

    }


}
