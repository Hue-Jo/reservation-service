package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public void requestReservation(ReservationDto reservationDto) {
        Reservation reservation = Reservation.builder()
                .email(reservationDto.getUserEmail())
                .userName(reservationDto.getUserName())
                .storeName(reservationDto.getStoreName())
                .reservationDt(reservationDto.getReservationDt())
                .build();
        reservationRepository.save(reservation);
    }

    @Override
    public ReservationDto confirmReservation(Long reservationId) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            return ReservationDto.builder()
                    .userEmail(reservation.getEmail())
                    .userName(reservation.getUserName())
                    .storeName(reservation.getStoreName())
                    .reservationDt(reservation.getReservationDt())
                    .build();
        }
        return null;  // 예외처리
    }

    @Override
    public void updateReservation(Long reservationId, ReservationDto reservationDto) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setReservationDt(reservationDto.getReservationDt());
            reservationRepository.save(reservation);
        } else {
            // 예외처리
        }
    }

    @Override
    public void cancelReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}