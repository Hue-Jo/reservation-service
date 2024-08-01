package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.constant.ReservationStatus;
import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.exception.error.StoreNotExistException;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.service.ReservationApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationApplyServiceImpl implements ReservationApplyService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<ReservationDto.Response> getReservationByDate(Long storeId, String specificDate) {
        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotExistException("존재하지 않는 매장입니다.");
        }

        // 문자열을 LocalDate로 변환
        LocalDate date;
        try {
            date = LocalDate.parse(specificDate, DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("날짜를 다시 입력해주세요. 올바른 형식은 yyyy-MM-dd입니다.");
        }

        // 날짜 범위 설정
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        // 예약 데이터 조회
        List<Reservation> reservations = reservationRepository.findByStore_StoreIdAndReservationDtBetween(storeId, startOfDay, endOfDay);

        return reservations.stream()
                .map(reservation -> ReservationDto.Response.builder()
                        .userEmail(reservation.getUser().getEmail())
                        .userName(reservation.getUser().getUserName())
                        .reservationDt(reservation.getReservationDt())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 일요일의 경우 예약 거절, 그외에는 승인
     */
    @Override
    public ResponseEntity<String> processReservation(Long reservationId, boolean approve) {

        Optional<Reservation> reservationOptional = reservationRepository.findByReservationId(reservationId);
        if (reservationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약번호가 존재하지 않습니다.");
        }

        Reservation reservation = reservationOptional.get();
        LocalDateTime reservationDateTime = reservation.getReservationDt();
        LocalDate reservationDate = reservationDateTime.toLocalDate();

        // 일요일 예약 거절
        if (reservationDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            reservation.setStatus(ReservationStatus.REJECTED);  // 일요일 예약은 거절 상태로 변경
            reservationRepository.save(reservation);
            return ResponseEntity.ok("일요일 예약은 불가합니다. 다른 요일로 다시 예약해주세요.");
        }

        // 일요일이 아닌 경우 예약 승인
        reservation.setStatus(ReservationStatus.APPROVED);
        reservationRepository.save(reservation);
        return ResponseEntity.ok("예약이 승인되었습니다.");
    }
}