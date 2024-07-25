package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.entity.User;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    /**
     * 예약신청
     */
    @Override
    public Long requestReservation(ReservationDto.Request reservationDto) {

        Optional<User> user = userRepository.findByEmail(reservationDto.getUserEmail());
        if(user.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 회원입니다.");
        }
        Optional<Store> store = storeRepository.findByStoreName(reservationDto.getStoreName());
        if(store.isEmpty()) {
            throw new StoreNotExistException("존재하지 않는 상호명입니다");
        }

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
     * 예약정보 확인 (예약번호 입력)
     */
    @Override
    public Optional<ReservationDto.Request> confirmReservation(Long reservationId) {
        return reservationRepository.findByReservationId(reservationId)
                .map(reservation -> ReservationDto.Request.builder()
                        .userEmail(reservation.getUserEmail())
                        .userName(reservation.getUserName())
                        .storeName(reservation.getStoreName())
                        .reservationDt(reservation.getReservationDt())
                        .build());

    }


    /**
     * 예약정보 수정 (예약번호 입력)
     */
    @Override
    public ResponseEntity<String> updateReservation(Long reservationId, ReservationDto.UpdateDt updateDt) {

        Optional<Reservation> reservation = reservationRepository.findByReservationId(reservationId);

        if (reservation.isPresent()) {
            Reservation currnetReservation = reservation.get();
            LocalDateTime newDateTime = updateDt.getUpdatedDt();

            currnetReservation.setReservationDt(newDateTime);
            reservationRepository.save(currnetReservation);

            String message = String.format("예약일자가 %s로 변경되었습니다.", newDateTime);
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 예약번호가 존재하지 않습니다.");
        }
    }


    /**
     * 예약취소 (예약번호 입력)
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


    /**
     * 방문확인
     * 10분 이상 지각시, 30분 후 이용가능
     * 미뤄진 예약 시간에 이미 예약자가 있는 경우, 예약을 새롭게 해야한다는 메시지 반환
     */
    @Override
    public String confirmVisit(Long reservationId) {

        Optional<Reservation> optionalReservation = reservationRepository.findByReservationId(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime reservationDt = reservation.getReservationDt();

            if (now.isAfter(reservationDt.plusMinutes(10))) { // 10분 지각시,
                LocalDateTime newReservationDt = reservationDt.plusMinutes(30); // 30분 뒤로 연기

                // 미룬 시간에 다른 예약이 있는지 확인
                List<Reservation> existingReservation = reservationRepository.findByReservationDt(newReservationDt);

                if (!existingReservation.isEmpty()) {
                    return "연기된 시간에 이미 다른 예약이 있습니다. 다른 날짜/시간으로 예약을 다시 해주세요";
                } else {
                    reservation.setDelayYn(true);
                    reservation.setNewReservationDt(newReservationDt);
                    reservationRepository.save(reservation);
                    return "예약시간보다 10분이상 초과되었습니다. 예약이 30분 후로 연기되었습니다.";
                }
            } else {
                reservation.setVisitYn(true);
                reservationRepository.save(reservation);
                return "방문 확인되었습니다. 이용해주셔서 감사합니다.";
            }
        }
        return "예약번호를 찾을 수 없습니다.";
    }


}

