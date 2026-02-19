package com.jumbotail.shippingchargeestimator.exceptions;

public class ResourceNotFoundException extends BaseException{
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }
}
