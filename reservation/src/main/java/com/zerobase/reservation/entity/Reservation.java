package com.zerobase.reservation.entity;

import com.zerobase.reservation.constant.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime reservationDt;

    private boolean visitYn;
    private boolean delayYn;
    private LocalDateTime newReservationDt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
