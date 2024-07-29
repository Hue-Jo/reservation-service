package com.zerobase.reservation.service;

import com.zerobase.reservation.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    // 리뷰작성(실 방문자/이용자만 )
    ReviewDto writeReview(ReviewDto reviewDto);

    // 매장별 리뷰 확인
    List<ReviewDto> readReviewsByStoreName(String storeName);

    // 리뷰 수정 (작성자 ONLY)
    ReviewDto updateReview(ReviewDto reviewDto, String userEmail);

    // 리뷰 삭제 (작성지 & 관리자 ONLY)
    void deleteReview(Long reviewId, String userEmail);

}
