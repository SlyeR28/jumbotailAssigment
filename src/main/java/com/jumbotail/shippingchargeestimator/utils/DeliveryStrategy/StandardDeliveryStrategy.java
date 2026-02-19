package com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy;

public class StandardDeliveryStrategy implements DeliveryStrategy {

    /**
     * Calculate additional charge based on product weight.
     *
     * @param weight Product weight in kg
     * @return Extra charge amount
     */
    @Override
    public double calculateExtraCharge(double weight) {
        return 0.0;
    }

    /**
     * Check if this strategy is applicable for the given speed type.
     *
     * @param speed Speed type (e.g., "standard", "express")
     * @return true if applicable
     */
    @Override
    public boolean isApplicable(String speed) {
        return speed == null || speed.equalsIgnoreCase("standard");
    }
}
