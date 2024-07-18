package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.repository.ReviewRepository;
import com.zerobase.reservation.service.ReservationSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReservationSerivce {

    private final ReviewRepository reviewRepository;
}
