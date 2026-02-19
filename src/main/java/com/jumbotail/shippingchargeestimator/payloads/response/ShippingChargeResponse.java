package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingChargeResponse {

    private Long id;

    private Long warehouseId;

    private Long customerId;

    private double distanceKm;

    private String deliverySpeed;

    private double shippingCharge;
}
