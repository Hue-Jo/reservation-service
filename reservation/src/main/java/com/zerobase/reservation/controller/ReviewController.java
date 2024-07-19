package com.zerobase.reservation.controller;

import com.zerobase.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 (실고객 only)
     */
    @PostMapping("/wrtie")
    public void writeReview() {

    }


    /**
     * 리뷰 확인
     */
    @GetMapping("/read")
    public void readReview() {

    }


    /**
     * 리뷰 수정 (실고객 only)
     */
    @PatchMapping("/edit")
    public void updateReview() {
    }


    /**
     * 리뷰 삭제 (실고객 & 매니저 only)
     */
    @DeleteMapping("/delte")
    public void deleteReview() {

    }


}
