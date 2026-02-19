package com.jumbotail.shippingchargeestimator.mapper;

import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import com.jumbotail.shippingchargeestimator.payloads.request.SellerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.SellerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(target = "latitude" , source = "location.latitude")
    @Mapping(target = "longitude" , source = "location.longitude")
    @Mapping(target = "name" , source = "name")
    SellerResponse toResponse(Seller seller);



    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "products" , ignore = true)
    @Mapping(source = "sellerName" , target = "name")
    @Mapping(source = "latitude" , target = "location.latitude")
    @Mapping(source = "longitude" , target = "location.longitude")
    Seller toEntity(SellerRequest sellerRequest);
}
