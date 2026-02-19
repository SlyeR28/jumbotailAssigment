package com.jumbotail.shippingchargeestimator.services;

import com.jumbotail.shippingchargeestimator.payloads.request.OrderRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse confirmOrderBySeller(Long orderId);

    OrderResponse assignWarehouse(Long orderId);

    OrderResponse markAsReachedWarehouse(Long orderId);

}
