package com.zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private String userName;   // 리뷰 작성자
    private String title;      // 제목
    private String content;    // 내용
    private int rating;        // 별점 (1-5)
}
