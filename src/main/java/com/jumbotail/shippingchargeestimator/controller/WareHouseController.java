package com.jumbotail.shippingchargeestimator.controller;

import com.jumbotail.shippingchargeestimator.payloads.request.WarehouseRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import com.jumbotail.shippingchargeestimator.services.WareHouseServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/warehouse")
public class WareHouseController {

    private final WareHouseServices wareHouseServices;

    @PostMapping("/create")
    public ResponseEntity<WarehouseResponse> createWarehouse(@Valid @RequestBody WarehouseRequest warehouseRequest) {
        return new ResponseEntity<>(wareHouseServices.createWarehouse(warehouseRequest), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouse(@PathVariable Long id) {
        return new ResponseEntity<>(wareHouseServices.getWarehouseById(id), HttpStatus.OK);
    }


    @GetMapping("/nearest")
    public ResponseEntity<WarehouseResponse> closestWarehouse(
            @RequestParam long sellerId,
            @RequestParam long orderId) {

        return new ResponseEntity<>(wareHouseServices.findClosestWarehouse(sellerId , orderId), HttpStatus.OK);
    }

}
