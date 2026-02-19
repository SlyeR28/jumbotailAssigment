package com.jumbotail.shippingchargeestimator.utils.ShiipingStragery;

import org.springframework.stereotype.Component;

@Component
public class TruckShipping implements ShippingStrategy{

    /**
     * return true if strategy works
     *
     * @param distance
     * @return
     */
    @Override
    public boolean isApplicable(double distance) {
        return distance > 100 && distance < 500;
    }

    /**
     * Calculate shipping cost based on distance (Km) and Weight(kg)
     *
     * @param distance
     * @param weight
     */
    @Override
    public double calculate(double distance, double weight) {

       return distance * weight*2;
    }
}
