package com.jumbotail.shippingchargeestimator.controller;

import com.jumbotail.shippingchargeestimator.payloads.request.ShippingChargeRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CalculateShippingResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.ShippingChargeResponse;
import com.jumbotail.shippingchargeestimator.services.ShippingChargesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shipping-charge")
public class ShippingChargeController {

    private final ShippingChargesService shippingChargeService;

    @GetMapping
    public ResponseEntity<ShippingChargeResponse> getShippingCharge(
            @RequestParam Long warehouseId,
            @RequestParam Long customerId,
            @RequestParam String deliverySpeed) {
        return ResponseEntity.ok(shippingChargeService.calculateShippingCharge(warehouseId, customerId , deliverySpeed));
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculateShippingResponse> calculateShippingCharge(
            @RequestBody ShippingChargeRequest request) {
        return ResponseEntity.ok(shippingChargeService.calculateShippingForSeller(request));
    }
}