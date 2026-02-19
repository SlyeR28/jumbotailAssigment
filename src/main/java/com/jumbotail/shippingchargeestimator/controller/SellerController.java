package com.jumbotail.shippingchargeestimator.controller;

import com.jumbotail.shippingchargeestimator.payloads.request.SellerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.SellerResponse;
import com.jumbotail.shippingchargeestimator.services.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/seller")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/create")
    public ResponseEntity<SellerResponse> createSeller(@Valid @RequestBody SellerRequest sellerRequest) {
        return new ResponseEntity<>(sellerService.createSeller(sellerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<SellerResponse>> getAllSellers(Pageable pageable) {
        return ResponseEntity.ok(sellerService.getSellers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getSellerById(@PathVariable long id) {
        return ResponseEntity.ok(sellerService.getSellerById(id));

    }
}
