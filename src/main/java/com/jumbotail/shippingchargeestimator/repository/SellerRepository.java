package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
