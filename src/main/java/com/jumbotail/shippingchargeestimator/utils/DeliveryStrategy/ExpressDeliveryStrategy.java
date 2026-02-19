package com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy;

import org.springframework.stereotype.Component;

@Component
public class ExpressDeliveryStrategy implements DeliveryStrategy {

    private static final double EXTRA_CHARGE_PER_KG = 1.2;

    /**
     * Calculate additional charge based on product weight.
     *
     * @param weight Product weight in kg
     * @return Extra charge amount
     */
    @Override
    public double calculateExtraCharge(double weight) {
        return weight * EXTRA_CHARGE_PER_KG;
    }

    /**
     * Check if this strategy is applicable for the given speed type.
     *
     * @param speed Speed type (e.g., "standard", "express")
     * @return true if applicable
     */
    @Override
    public boolean isApplicable(String speed) {
        return speed != null && speed.equalsIgnoreCase("express");
    }
}
