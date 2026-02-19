package com.jumbotail.shippingchargeestimator.exceptions;

public class SellerNotFoundException extends ResourceNotFoundException {
    public SellerNotFoundException(String message) {
        super(message);
    }
}
