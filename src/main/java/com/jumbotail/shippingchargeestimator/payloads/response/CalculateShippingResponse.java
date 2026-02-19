package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculateShippingResponse {

    private double shippingCharge;
    private WarehouseResponse nearestWarehouse;
}
