package com.jumbotail.shippingchargeestimator.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name" , nullable = false , length = 100)
    private String name;

    @Column(name = "selling_price" , nullable = false )
    private double price;


    @Column(nullable = false)
    private boolean inStock;

    @Column(nullable = false)
    private int remainingQuantity;

    @Embedded
    private Dimensions dimensions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id" ,  nullable = false)
    private Seller seller;

    @PrePersist
    @PreUpdate
    private void updateStockStatus(){
        this.inStock = this.remainingQuantity > 0;
    }

}
