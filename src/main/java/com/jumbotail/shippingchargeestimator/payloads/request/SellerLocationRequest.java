package com.jumbotail.shippingchargeestimator.payloads.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerLocationRequest {

    @NotNull
    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")


    private double latitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private double longitude;
}
