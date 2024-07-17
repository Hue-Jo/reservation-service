package com.zerobase.reservation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

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
