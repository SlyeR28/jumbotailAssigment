package com.jumbotail.shippingchargeestimator.payloads.response;

import lombok.*;

@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private double price;

    private boolean inStock;
    private int remainingQuantity;

    private double weight;
    private double length;
    private double width;
    private double height;

    private Long sellerId;


}
