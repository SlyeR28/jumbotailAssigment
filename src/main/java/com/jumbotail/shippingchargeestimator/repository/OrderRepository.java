package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.Order;
import com.jumbotail.shippingchargeestimator.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository  extends JpaRepository<Order, Long> {

    List<Order> findBySellerId(Long sellerId);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.product", "seller", "customer"})
    Optional<Order> findWithItemsById(Long id);
}
