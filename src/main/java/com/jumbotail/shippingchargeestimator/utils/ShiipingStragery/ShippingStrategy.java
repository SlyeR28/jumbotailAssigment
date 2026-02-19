package com.jumbotail.shippingchargeestimator.utils.ShiipingStragery;

public interface ShippingStrategy  {
    /**
     * Calculate shipping cost based on distance (Km) and Weight(kg)
     */
    double calculate(double distance , double weight);

    /**
     * return true if strategy works
     * @param distance
     * @return
     */
    boolean isApplicable(double distance);

}
