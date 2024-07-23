package com.zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    @NotBlank(message = "이메일은 필수 작성항목입니다.")
    private String userEmail;   // 이메일(아이디)

    @NotBlank(message = "예약자명은 필수 작성항목입니다.")
    private String userName;    // 예약자 이름

    @NotBlank(message = "예약하고자 하는 상호명을 작성하십시오")
    private String storeName;   // 가게명

    @NotBlank(message = "예약하고자 하는 날짜를 작성하십시오")
    private LocalDateTime reservationDt;  // 예약 일자

}
