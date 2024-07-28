package com.zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;

    @NotBlank(message = "이용한 매장명을 작성하십시오")
    private String storeName;  // 매장명

    private String userName;   // 리뷰 작성자

    @NotBlank(message = "제목을 작성해주세요")
    private String title;      // 제목

    @NotBlank(message = "내용을 작성해주세요")
    @Size(min = 20, message = "내용은 20자 이상 작성해야 합니다.")
    private String content;    // 내용

    @NotBlank(message = "1-5점 사이의 별점을 작성해주세요")
    @Min(value = 1, message = "별점은 1점부터 줄 수 있습니다.")
    @Max(value = 5, message = "별점은 5점까지만 줄 수 있습니다.")
    private int rating;        // 별점 (1-5)

    private Long reservationId;
}
