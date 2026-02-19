package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
   Optional<Shipping> findTopByCustomerIdAndWarehouseIdOrderByShippedAtDesc(Long customerId , Long warehouseId);
}
