package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.dto.ReviewDto;
import com.zerobase.reservation.entity.Review;
import com.zerobase.reservation.exception.ReviewNotExistException;
import com.zerobase.reservation.repository.ReviewRepository;
import com.zerobase.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    /**
     * 리뷰 작성 (실이용자 ONLY)
     */
    @Override
    public ReviewDto writeReview(ReviewDto reviewDto) {

        Review review = Review.builder()
                .storeName(reviewDto.getStoreName())
                .userName(reviewDto.getUserName())
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .build();

        review = reviewRepository.save(review);
        return ReviewDto.builder()
                .storeName(review.getStoreName())
                .userName(review.getUserName())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }


    /**
     * 리뷰 확인
     */
    @Override
    public List<ReviewDto> readReviewsByStoreName(String storeName) {
        List<Review> reviews = reviewRepository.findByStoreName(storeName);
        return reviews.stream()
                .map(review -> ReviewDto.builder()
                        .userName(review.getUserName())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .build())
                .collect(Collectors.toList());

    }

    /**
     * 리뷰 수정 (실이용자 ONLY)
     * 제목, 내용, 별점만 수정 가능
     */
    @Override
    public ReviewDto updateReview(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getReviewId())
                .orElseThrow(() -> new ReviewNotExistException("해당 리뷰가 존재하지 않습니다."));

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        Review updateReview = reviewRepository.save(review);

        return ReviewDto.builder()
                .reviewId(updateReview.getReviewId())
                .storeName(updateReview.getStoreName())
                .userName(updateReview.getUserName())
                .title(updateReview.getTitle())
                .content(updateReview.getContent())
                .rating(updateReview.getRating())
                .build();
    }


    /**
     * 리뷰삭제 (실이용자 & MANAGER ONLY)
     */
    @Override
    public void deleteReview(Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new ReviewNotExistException("해당 리뷰가 존재하지 않습니다.");
        }
    }


}