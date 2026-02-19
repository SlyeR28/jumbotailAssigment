package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SellerResponse {

    private Long id;

    private String name;

    private String phoneNumber;


    private double latitude;

    private double longitude;

}
