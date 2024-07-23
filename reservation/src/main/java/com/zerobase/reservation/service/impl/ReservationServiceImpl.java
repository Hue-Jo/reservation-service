package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * 예약신청
     */
    @Override
    public Long requestReservation(ReservationDto reservationDto) {
        Reservation reservation = Reservation.builder()
                .userEmail(reservationDto.getUserEmail())
                .userName(reservationDto.getUserName())
                .storeName(reservationDto.getStoreName())
                .reservationDt(reservationDto.getReservationDt())
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation.getReservationId(); // 예약번호 반환
    }


    /**
     * 예약정보 확인
     */
    @Override
    public Optional<ReservationDto> confirmReservation(Long reservationId) {
        return reservationRepository.findByReservationId(reservationId)
                .map(reservation -> ReservationDto.builder()
                        .userEmail(reservation.getUserEmail())
                        .userName(reservation.getUserName())
                        .storeName(reservation.getStoreName())
                        .reservationDt(reservation.getReservationDt())
                        .build());

    }


    /**
     * 예약정보 수정
     */
    @Override
    public ResponseEntity<String> updateReservation(Long reservationId, ReservationDto reservationDto) {

        Optional<Reservation> reservation = reservationRepository.findByReservationId(reservationId);

        if (reservation.isPresent()) {
            Reservation currnetReservation = reservation.get();
            LocalDateTime newDateTime = reservationDto.getReservationDt();

            currnetReservation.setReservationDt(newDateTime);
            reservationRepository.save(currnetReservation);

            String message = String.format("예약일자가 %s로 변경되었습니다.", newDateTime);
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약번호가 존재하지 않습니다.");
        }
    }


    /**
     * 예약취소
     */
    @Override
    public ResponseEntity<String> cancelReservation(Long reservationId) {

        Optional<Reservation> reservation = reservationRepository.findByReservationId(reservationId);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
            return ResponseEntity.ok("예약이 성공적으로 취소되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약번호입니다.");
        }

    }
}

