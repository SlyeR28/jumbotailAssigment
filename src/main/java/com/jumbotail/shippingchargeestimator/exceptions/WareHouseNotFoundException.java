package com.jumbotail.shippingchargeestimator.exceptions;

public class WareHouseNotFoundException extends RuntimeException {
    public WareHouseNotFoundException(String message) {
        super(message);
    }
}
