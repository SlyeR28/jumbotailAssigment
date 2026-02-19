package com.jumbotail.shippingchargeestimator.payloads.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull(message = "Product Id is Required")
    private long productId;

    @NotNull
    @Positive(message = "Quantity must be greater then 0")
    private int quantity;

}
