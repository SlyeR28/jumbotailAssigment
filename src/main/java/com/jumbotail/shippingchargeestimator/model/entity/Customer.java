package com.jumbotail.shippingchargeestimator.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "kirana_stores" ,
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone_numer")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "shop_name" , nullable = false , length = 100)
    private String shopName;

    @Column(name = "phone_number" , nullable = false ,length = 15)
    private String phoneNumber;

    @Embedded
    private  Location location;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


}
