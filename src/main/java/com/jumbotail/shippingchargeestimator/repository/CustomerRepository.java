package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
