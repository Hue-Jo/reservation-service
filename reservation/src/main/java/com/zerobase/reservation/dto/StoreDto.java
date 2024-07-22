package com.zerobase.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    @NotBlank(message = "상호명을 적어주세요")
    private String storeName;      // 가게명

    @NotBlank(message = "가게의 위치를 작성해주세요")
    private String storeLocation;  // 가게 위치

    @NotBlank(message = "가게의 전화번호를 작성해주세요")
    private String storePhoneNum;  // 가게 전화번호

    @NotBlank(message = "오픈시간을 작성해주세요")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime storeOpenTime;  // 오픈시간

    @NotBlank(message = "마감시간을 작성해주세요")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime storeCloseTime; // 마감시간
}
