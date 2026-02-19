package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
