package com.jumbotail.shippingchargeestimator.services;

import com.jumbotail.shippingchargeestimator.payloads.request.ShippingChargeRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CalculateShippingResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.ShippingChargeResponse;

public interface ShippingChargesService {

    ShippingChargeResponse calculateShippingCharge(Long warehouseId , Long customerId , String deliverySpeed);

    CalculateShippingResponse calculateShippingForSeller(ShippingChargeRequest shippingCharge);
}
