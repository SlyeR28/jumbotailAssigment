package com.jumbotail.shippingchargeestimator.payloads.response;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

    private Long id;

    private String shopName;

    private String phoneNumber;

    private double latitude;

    private double longitude;

    private LocalDateTime createdAt;
}


