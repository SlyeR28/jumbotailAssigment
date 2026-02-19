package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId;

    private Long customerId;
    private String customerName;

    private Long sellerId;
    private String sellerName;

    private Long warehouseId;
    private String warehouseName;

    private String status;


    private double shippingCharge;

    private double totalOrderAmount;

    private List<OrderItemResponse> items;

    private String updatedAt;
}
