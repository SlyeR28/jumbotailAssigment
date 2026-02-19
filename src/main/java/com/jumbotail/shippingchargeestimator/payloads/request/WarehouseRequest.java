package com.jumbotail.shippingchargeestimator.payloads.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseRequest {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotNull(message = "Latitude is Required")
    @DecimalMin(value = "-90.0" , message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0" , message = "Latitude must be <= 90")
    private double latitude;

    @NotNull(message = "Longitude is Required")
    @DecimalMin(value = "-180.0" , message = "Longitude must be >= -180")
    @DecimalMax(value = "90.0" , message = "Longitude must be <= 180")
    private double longitude;
}
