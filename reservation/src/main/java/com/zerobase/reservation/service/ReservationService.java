package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReservationDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ReservationService {

    Long requestReservation(ReservationDto reservationDto);

    Optional<ReservationDto> confirmReservation(Long reservationId);

    ResponseEntity<String> updateReservation(Long reservationId, ReservationDto reservationDto);

    ResponseEntity<String> cancelReservation(Long reservationId);


}
