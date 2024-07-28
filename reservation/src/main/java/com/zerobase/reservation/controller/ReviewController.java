package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReviewDto;
import com.zerobase.reservation.exception.error.InvalidRoleException;
import com.zerobase.reservation.exception.error.ReviewNotExistException;
import com.zerobase.reservation.exception.error.UserNotFoundException;
import com.zerobase.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 (실고객 only)
     */
    @PostMapping("/wrtie")
    public ResponseEntity<ReviewDto> writeReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto writeReviewDto = reviewService.writeReview(reviewDto);
        return new ResponseEntity<>(writeReviewDto, HttpStatus.CREATED);

    }


    /**
     * 리뷰 확인 (매장명)
     */
    @GetMapping("/read/{storeName}")
    public ResponseEntity<List<ReviewDto>> readReview(@PathVariable String storeName) {
        List<ReviewDto> reviews = reviewService.readReviewsByStoreName(storeName);
        return new ResponseEntity<>(reviews, HttpStatus.OK);

    }


    /**
     * 리뷰 수정 (실고객 only)
     */
    @PatchMapping("/edit")
    public ResponseEntity<ReviewDto> updateReview(@RequestBody ReviewDto reviewDto,
                                                  @RequestHeader("user-email") String userEmail) {

        try {
            ReviewDto updateReviewDto = reviewService.updateReview(reviewDto, userEmail);
            return new ResponseEntity<>(updateReviewDto, HttpStatus.OK);
        } catch (ReviewNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


    /**
     * 리뷰 삭제 (실고객 & 매니저 only)
     */
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, @RequestHeader("user-email") String userEmail) {

        try {
            reviewService.deleteReview(reviewId, userEmail);
            return ResponseEntity.ok("리뷰가 삭제되었습니다.");
        } catch (ReviewNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 리뷰가 존재하지 않아 삭제에 실패했습니다.");
        } catch (InvalidRoleException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자와 관리자만 삭제가 가능합니다.");
        }
    }


}
