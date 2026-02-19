package com.jumbotail.shippingchargeestimator.mapper;

import com.jumbotail.shippingchargeestimator.model.entity.OrderItem;
import com.jumbotail.shippingchargeestimator.payloads.response.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "totalPrice",
            expression = "java(orderItem.getQuantity() * orderItem.getPriceAtPurchase())")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

}
