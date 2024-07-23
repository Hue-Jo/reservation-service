package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto writeReview(ReviewDto reviewDto);

    List<ReviewDto> readReviewsByStoreName(String storeName);

    ReviewDto updateReview(ReviewDto reviewDto);

    void deleteReview(Long reviewId);

}
