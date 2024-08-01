package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.exception.error.StoreNotExistException;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.service.ReservationApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationApplyServiceImpl implements ReservationApplyService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<ReservationDto.Response> getReservationsByStoreAndDate(Long storeId, LocalDate date) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotExistException("존재하지 않는 매장입니다.");
        }

        // 날짜 범위 설정
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // 예약 데이터 조회
        List<Reservation> reservations = reservationRepository.findByStoreIdAndReservationDtBetween(storeId, startOfDay, endOfDay);

        return reservations.stream()
                .map(reservation -> ReservationDto.Response.builder()
                        .userEmail(reservation.getUser().getEmail())
                        .userName(reservation.getUser().getUserName())
                        .reservationDt(reservation.getReservationDt())
                        .build())
                .collect(Collectors.toList());
    }
}