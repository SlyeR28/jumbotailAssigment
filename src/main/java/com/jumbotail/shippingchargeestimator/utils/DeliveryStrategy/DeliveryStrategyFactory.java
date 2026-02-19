package com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryStrategyFactory {

    private final List<DeliveryStrategy> deliveryStrategies;

    public DeliveryStrategy create(String speed) {
        return deliveryStrategies.stream()
                .filter(s -> s.isApplicable(speed))
                .findFirst()
                .orElseGet(() ->
                        deliveryStrategies.stream()
                                .filter(s -> s instanceof StandardDeliveryStrategy)
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("DeliveryStrategy not found")));
    }

}
