package com.jumbotail.shippingchargeestimator.services;


import com.jumbotail.shippingchargeestimator.payloads.request.WarehouseRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;

public interface WareHouseServices {

    // create warehouse
    WarehouseResponse createWarehouse(WarehouseRequest warehouseRequest);


    //get warehouse by id
    WarehouseResponse getWarehouseById(Long id);

    // closest warehouse to seller
    WarehouseResponse findClosestWarehouse(Long sellerId , Long orderId);
}
