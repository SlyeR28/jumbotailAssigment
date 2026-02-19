package com.jumbotail.shippingchargeestimator.model.entity;


import com.jumbotail.shippingchargeestimator.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id" ,  nullable = false)
    private Customer customers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id" ,nullable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private WareHouse wareHouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false , length = 50)
    private OrderStatus orderStatus;



    @Column(name = "total_order_amount" ,nullable = false)
    private double totalAmount;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "order" , orphanRemoval = true)
    private List<OrderItem> orderItems =  new ArrayList<>();


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }


}
