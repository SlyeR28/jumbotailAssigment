package com.jumbotail.shippingchargeestimator.payloads.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is Required")
    private String name;

    @NotNull(message = "product price is Required")
    @Positive(message = "price must be positive")
    private double price;

    @NotNull
    @Min(value = 0 ,message = "Quantity can't be negative")
    private int remainingQuantity;

    @NotNull
    @Positive
    private double weight;

    @NotNull
    @Positive
    private double height;

    @NotNull
    @Positive
    private double width;

    @NotNull
    @Positive
    private double length;

    @NotNull
    private Long sellerId;

}
