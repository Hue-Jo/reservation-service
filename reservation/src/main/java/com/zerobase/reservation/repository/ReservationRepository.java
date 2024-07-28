package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long reservationId);

    boolean existsByReservationDt(LocalDateTime reservationDt);

    Optional<Reservation> findByReservationIdAndVisitYn(Long reservationId, boolean visitYn);
}
