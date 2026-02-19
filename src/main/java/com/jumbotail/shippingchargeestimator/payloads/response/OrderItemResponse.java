package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private Long productId;
    private String productName;

    private int  quantity;
    private double priceAtPurchase;

    private Double totalPrice;

}
