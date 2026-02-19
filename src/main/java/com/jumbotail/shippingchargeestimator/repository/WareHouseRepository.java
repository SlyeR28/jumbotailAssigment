package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
}
