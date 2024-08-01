package com.zerobase.reservation.service.impl;

import com.zerobase.reservation.constant.UserRole;
import com.zerobase.reservation.dto.ReviewDto;
import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.entity.Review;
import com.zerobase.reservation.entity.Store;
import com.zerobase.reservation.entity.User;
import com.zerobase.reservation.exception.error.InvalidWriterException;
import com.zerobase.reservation.exception.error.ReservationNotFoundException;
import com.zerobase.reservation.exception.error.InvalidRoleException;
import com.zerobase.reservation.exception.error.ReviewNotExistException;
import com.zerobase.reservation.exception.error.UserNotFoundException;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.ReviewRepository;
import com.zerobase.reservation.repository.UserRepository;
import com.zerobase.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 리뷰 작성 (실이용자 ONLY)
     */
    @Override
    public ReviewDto writeReview(ReviewDto reviewDto) {

        Reservation reservation = reservationRepository.findById(reviewDto.getReservationId())
                .orElseThrow(() -> new ReservationNotFoundException("해당 예약이 존재하지 않습니다."));

        if (!reservation.isVisitYn()) {
            throw new InvalidWriterException("매장을 실제로 방문/이용한 이용자만 리뷰를 작성할 수 있습니다.");
        }

        Store store = reservation.getStore();
        User user = reservation.getUser();

        Review review = Review.builder()
                .store(store)
                .user(user)
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .build();
        review = reviewRepository.save(review);

        return ReviewDto.builder()
                .reviewId((review.getReviewId()))
                .storeName(store.getStoreName())
                .userName(user.getUserName())
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

        if (storeName.trim().isEmpty()) {
            throw new IllegalArgumentException("매장명을 입력해 주세요.");
        }
        List<Review> reviews = reviewRepository.findByStore_StoreName(storeName);
        return reviews.stream()
                .map(review -> ReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .storeName(review.getStore().getStoreName())
                        .userName(review.getUser().getUserName())
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
    public ReviewDto updateReview(ReviewDto reviewDto, String userEmail) {

        Review review = reviewRepository.findById(reviewDto.getReviewId())
                .orElseThrow(() -> new ReviewNotExistException("해당 리뷰가 존재하지 않습니다."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 본인 확인
        if (!review.getUser().equals(user)) {
            throw new UserNotFoundException("리뷰 수정은 게시자 본인만 가능합니다.");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        Review updateReview = reviewRepository.save(review);

        return ReviewDto.builder()
                .reviewId(updateReview.getReviewId())
                .storeName(updateReview.getStore().getStoreName())
                .userName(updateReview.getUser().getUserName())
                .title(updateReview.getTitle())
                .content(updateReview.getContent())
                .rating(updateReview.getRating())
                .build();
    }


    /**
     * 리뷰삭제 (실이용자 & MANAGER ONLY)
     */
    @Override
    public void deleteReview(Long reviewId, String userEmail) {

        // 리뷰가 존재하는지 확인
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotExistException("해당 리뷰가 존재하지 않습니다."));

        // 현재 사용자의 권한 확인
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        // 현재 사용자의 역할 확인
        if (user.getRole() != UserRole.MANAGER && !review.getUser().equals(user)) {
            throw new InvalidRoleException("리뷰를 삭제할 권한이 없습니다.");
        }

        // 리뷰 삭제
        reviewRepository.deleteById(reviewId);
    }

}