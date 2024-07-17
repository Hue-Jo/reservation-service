package com.zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String description;
}
