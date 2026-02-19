package com.jumbotail.shippingchargeestimator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class ShippingChargeEstimatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingChargeEstimatorApplication.class, args);
    }

}
