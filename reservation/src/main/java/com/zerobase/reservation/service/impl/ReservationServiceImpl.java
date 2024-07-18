package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.service.ReservationSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationSerivce {

    private final ReservationRepository reservationRepository;
}
