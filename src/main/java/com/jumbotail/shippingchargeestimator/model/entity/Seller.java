package com.jumbotail.shippingchargeestimator.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers" ,
uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone_number")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Seller {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "seller_name" ,  nullable = true , length = 100)
    private String name;

    @Column(name = "phone_number" ,   nullable = true , length = 15)
    private String phoneNumber;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "seller",
            cascade = CascadeType.ALL ,
            orphanRemoval = true)
    private List<Product> products = new ArrayList<>();


}
