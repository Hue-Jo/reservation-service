package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.constant.ReservationStatus;
import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.entity.User;
import com.zerobase.reservation.exception.error.ReservationNotFoundException;
import com.zerobase.reservation.exception.error.StoreNotExistException;
import com.zerobase.reservation.exception.error.UserNotFoundException;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.StoreRepository;
import com.zerobase.reservation.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /**
     * 예약신청
     */
    @Override
    public Long requestReservation(ReservationDto.Request reservationDto, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

        Store store = storeRepository.findByStoreName(reservationDto.getStoreName())
                .orElseThrow(() -> new StoreNotExistException("존재하지 않는 상호명입니다."));

        Reservation reservation = Reservation.builder()
                .user(user)
                .store(store)
                .reservationDt(reservationDto.getReservationDt())
                .visitYn(false)
                .delayYn(false)
                .status(ReservationStatus.PENDING)
                .build();

        return reservationRepository.save(reservation)
                .getReservationId(); // 예약번호 반환
    }


    /**
     * 예약정보 확인 (예약번호 입력)
     */
    @Override
    public ResponseEntity<ReservationDto.Request> confirmReservation(Long reservationId) {

        Optional<Reservation> reservationOptional = reservationRepository.findByReservationId(reservationId);
        if (reservationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Reservation reservation = reservationOptional.get();

        ReservationDto.Request response = ReservationDto.Request.builder()
                .userEmail(reservation.getUser().getEmail())
                .userName(reservation.getUser().getUserName())
                .storeName(reservation.getStore().getStoreName())
                .reservationDt(reservation.getReservationDt())
                .build();
        return ResponseEntity.ok(response);

    }


    /**
     * 예약정보 수정 (예약번호 입력)
     */
    @Override
    public ResponseEntity<String> updateReservation(Long reservationId, ReservationDto.UpdateDt updateDt) {

        Optional<Reservation> reservationOptional = reservationRepository.findByReservationId(reservationId);
        if (reservationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약번호가 존재하지 않습니다.");
        }
        
        Reservation reservation = reservationOptional.get();
        LocalDateTime newDateTime = updateDt.getUpdatedDt();
        reservation.setReservationDt(newDateTime);
        reservationRepository.save(reservation);

        String message = String.format("예약일자가 %s로 변경되었습니다.", newDateTime);
        return ResponseEntity.ok(message);
    }


    /**
     * 예약취소 (예약번호 입력)
     */
    @Override
    public ResponseEntity<String> cancelReservation(Long reservationId) {

        if (!reservationRepository.existsById(reservationId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 예약번호입니다.");
        }

        reservationRepository.deleteById(reservationId);
        return ResponseEntity.ok("예약이 성공적으로 취소되었습니다.");
    }


    /**
     * 방문확인
     * 예약시간 10분 전부터만 방문확인 가능
     * 10분 이상 지각시, 30분 후 이용가능
     * 미뤄진 예약 시간에 이미 예약자가 있는 경우, 예약을 새롭게 해야한다는 메시지 반환
     */
    @Override
    public String confirmVisit(Long reservationId) {

        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("예약번호를 찾을 수 없습니다."));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDt = reservation.getReservationDt();
        LocalDateTime beforeReservation = reservationDt.minusMinutes(11);

        if (now.isBefore(beforeReservation)) {
            return "예약시간 10분 전부터 방문 확인이 가능합니다.";

        } else if (now.isAfter(reservationDt.plusMinutes(10))) { // 10분 지각 시
            LocalDateTime newReservationDt = reservationDt.plusMinutes(30); // 30분 뒤로 연기

            // 미룬 시간에 다른 예약이 있는지 확인
            boolean alreadyExistingReservation = reservationRepository.existsByReservationDt(newReservationDt);
            if (alreadyExistingReservation) {
                return "연기된 시간에 이미 다른 예약이 있습니다. 다른 날짜/시간으로 예약을 다시 해주세요.";
            } else {
                reservation.setDelayYn(true);
                reservation.setNewReservationDt(newReservationDt);
                reservationRepository.save(reservation);
                return "예약시간보다 10분 이상 초과되었습니다. 예약이 30분 후로 연기되었습니다.";
            }
        } else {
            reservation.setVisitYn(true);
            reservationRepository.save(reservation);
            return "방문 확인되었습니다. 이용해주셔서 감사합니다.";
        }
    }
}

