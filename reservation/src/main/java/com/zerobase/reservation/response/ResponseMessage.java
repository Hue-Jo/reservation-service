package com.zerobase.reservation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {

    private ResponseMessageHeader header;
    private Object body;

    public static ResponseMessage fail(String message) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .resultMessage(message)
                        .resultStatus(HttpStatus.BAD_REQUEST.value())
                        .build())
                .body(null)
                .build();
    }

    public static ResponseMessage success(String message) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultMessage(message)
                        .resultStatus(HttpStatus.OK.value())
                        .build())
                .body(null)
                .build();
    }
}
