package com.zerobase.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zerobase.reservation.constant.ReservationStatus;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "예약하고자 하는 상호명을 작성하십시오")
        private String storeName;   // 가게명

        @NotBlank(message = "이메일은 필수 작성항목입니다.")
        @Email(message = "이메일 형식에 맞게 입력해주세요")
        private String userEmail;   // 이메일(아이디)

        @NotBlank(message = "예약자명은 필수 작성항목입니다.")
        private String userName;    // 예약자 이름

        @NotNull(message = "예약하고자 하는 날짜를 작성하십시오")
        @JsonFormat(pattern = "yyyy년 MM월 dd일, HH시 mm분")
        private LocalDateTime reservationDt;  // 예약 일자
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateDt {

        @NotNull(message = "변경하고자 하는 날짜를 작성하십시오")
        @JsonFormat(pattern = "yyyy년 MM월 dd일, HH시 mm분")
        private LocalDateTime updatedDt;  // 예약 일자
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String userEmail;   // 사용자 이메일
        private String userName;    // 사용자 이름
        @JsonFormat(pattern = "yyyy년 MM월 dd일, HH시 mm분")
        private LocalDateTime reservationDt;  // 예약 일자 및 시간
        private ReservationStatus status;
    }
}
