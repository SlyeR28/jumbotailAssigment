package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseResponse {

    private Long id;
    private String  name;

    private double latitude;
    private double longitude;

    private double distanceKm;

}
