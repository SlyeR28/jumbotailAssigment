package com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy;

public interface DeliveryStrategy {

    /**
     Calculate additional charge based on product weight.
     *
     * @param weight Product weight in kg
     * @return Extra charge amount
     */
    double calculateExtraCharge(double weight);

    /**
     * Check if this strategy is applicable for the given speed type.
     *
     * @param speed Speed type (e.g., "standard", "express")
     * @return true if applicable
     */
    boolean isApplicable(String speed);
}
