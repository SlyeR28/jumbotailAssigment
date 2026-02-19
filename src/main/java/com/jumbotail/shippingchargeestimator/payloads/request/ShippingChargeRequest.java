package com.jumbotail.shippingchargeestimator.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingChargeRequest {

    @NotNull(message = "Seller ID is required")
    private Long sellerId;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    @NotBlank(message = "Delivery speed is required")
    private String deliverySpeed;
}
