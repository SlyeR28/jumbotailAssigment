package com.jumbotail.shippingchargeestimator.exceptions;

public class InvalidOrderException extends BusinessException{

    public InvalidOrderException(String message) {
        super(message, "INVALID_ORDER");
    }
}
