package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationId(Long reservationId);

    boolean existsByReservationDt(LocalDateTime reservationDt);

    List<Reservation> findByStore_StoreIdAndReservationDtBetween(Long storeId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
