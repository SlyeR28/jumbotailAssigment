package com.jumbotail.shippingchargeestimator.utils.ShiipingStragery;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShippingStargeryFactory {

    private final List<ShippingStrategy> strategies;

    public ShippingStargeryFactory(List<ShippingStrategy> strategies) {
        this.strategies = strategies;
    }

    public ShippingStrategy create(double distance){
        return strategies.stream()
                .filter(s -> s.isApplicable(distance))
                .findFirst()
                .orElseThrow(()->new RuntimeException("Shipping Strategy not found"));
    }
}
