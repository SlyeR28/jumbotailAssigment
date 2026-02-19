package com.jumbotail.shippingchargeestimator.mapper;

import com.jumbotail.shippingchargeestimator.model.entity.Order;
import com.jumbotail.shippingchargeestimator.payloads.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {OrderItemMapper.class})
public interface OrderMapper {


    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "customerId", source = "customers.id")
    @Mapping(target = "customerName", source = "customers.shopName")
    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "sellerName", source = "seller.name")
    @Mapping(target = "warehouseId", source = "wareHouse.id")
    @Mapping(target = "warehouseName", source = "wareHouse.name")
    @Mapping(target = "status", source = "orderStatus")
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "updatedAt",
            expression = "java(order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null)")

    OrderResponse toResponse(Order order);
}
