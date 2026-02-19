package com.jumbotail.shippingchargeestimator.controller;

import com.jumbotail.shippingchargeestimator.payloads.request.OrderRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.OrderResponse;
import com.jumbotail.shippingchargeestimator.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest orderRequest) {
        return new  ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<OrderResponse> confirmOrderBySeller(@PathVariable long orderId){
        return new  ResponseEntity<>(orderService.confirmOrderBySeller(orderId), HttpStatus.OK);
    }

    @PutMapping("/assign-warehouse/{orderId}")
    public ResponseEntity<OrderResponse> assignWarehouse(@PathVariable long orderId){
        return new  ResponseEntity<>(orderService.assignWarehouse(orderId), HttpStatus.OK);
    }

    @PutMapping("/mark-reached-warehouse/{orderId}")
    public ResponseEntity<OrderResponse> markReachedWarehouse(@PathVariable long orderId){
        return new ResponseEntity<>(orderService.markAsReachedWarehouse(orderId), HttpStatus.OK);
    }

}
