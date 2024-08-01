package com.zerobase.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    @NotBlank(message = "상호명을 적어주세요")
    private String storeName;      // 가게명

    @NotBlank(message = "가게의 위치를 작성해주세요")
    private String location;  // 가게 위치

    @NotBlank(message = "가게의 전화번호를 작성해주세요")
    private String phoneNum;  // 가게 전화번호

    @NotNull(message = "오픈시간을 작성해주세요 (00:00 형식으로 작성해주세요)")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;  // 오픈시간

    @NotNull(message = "마감시간을 작성해주세요 (00:00 형식으로 작성해주세요)")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime; // 마감시간


}
