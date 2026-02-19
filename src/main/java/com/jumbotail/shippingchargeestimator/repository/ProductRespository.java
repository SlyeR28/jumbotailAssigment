package com.jumbotail.shippingchargeestimator.repository;

import com.jumbotail.shippingchargeestimator.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRespository   extends JpaRepository<Product, Long> {
    Page<Product> findProductBySeller_Id(Long sellerId , Pageable pageable);

    List<Product> findByInStockTrue();
}
