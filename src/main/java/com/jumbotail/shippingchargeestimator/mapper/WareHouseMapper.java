package com.jumbotail.shippingchargeestimator.mapper;

import com.jumbotail.shippingchargeestimator.model.entity.WareHouse;
import com.jumbotail.shippingchargeestimator.payloads.request.WarehouseRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WareHouseMapper {

    @Mapping(target = "latitude" , source = "location.latitude")
    @Mapping(target = "longitude" , source = "location.longitude")
    @Mapping(target = "distanceKm" , ignore = true)
    WarehouseResponse toResponse(WareHouse wareHouse);

    @Mapping(target = "id" ,  ignore = true)
    @Mapping(target = "location.latitude" , source = "latitude")
    @Mapping(target = "location.longitude" , source = "longitude")
    WareHouse toEntity(WarehouseRequest request);

    @Mapping(target = "location.latitude", source = "latitude")
    @Mapping(target = "location.longitude", source = "longitude")
    WareHouse toEntity(WarehouseResponse response);
}
