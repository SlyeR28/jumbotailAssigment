package com.jumbotail.shippingchargeestimator.exceptions;

public class BusinessException extends BaseException {

    public BusinessException(String message, String errorCode) {
        super(message, errorCode);
    }
}
