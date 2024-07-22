package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReservationDto;

public interface ReservationService {

    void requestReservation(ReservationDto reservationDto);

    ReservationDto confirmReservation(Long reservationId);

    void updateReservation(Long reservationId, ReservationDto reservationDto);

    void cancelReservation(Long reservationId);
}
