package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.repository.ReviewRepository;
import com.zerobase.reservation.service.ReservationService;
import com.zerobase.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
}