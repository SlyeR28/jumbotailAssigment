package com.jumbotail.shippingchargeestimator.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Location {


    @Column(nullable = false )
    private  double latitude;

    @Column(nullable = false )
    private  double longitude;


}
