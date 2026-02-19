package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private int status;
    private String errorCode;
    private String error;
    private LocalDateTime timestamp;
}
