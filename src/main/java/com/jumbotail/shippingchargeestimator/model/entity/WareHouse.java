package com.jumbotail.shippingchargeestimator.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "warehouses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WareHouse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "warehouse_name" ,  nullable = true , length = 100)
    private String name;

    @Embedded
    private Location location;







}
