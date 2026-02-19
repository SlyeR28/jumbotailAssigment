package com.jumbotail.shippingchargeestimator.mapper;

import com.jumbotail.shippingchargeestimator.model.entity.Customer;
import com.jumbotail.shippingchargeestimator.payloads.request.CustomerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mapping(target = "latitude" , source = "location.latitude")
    @Mapping(target = "longitude" , source = "location.longitude")
    @Mapping(target = "createdAt",
            source = "createdAt",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "location.latitude" , source = "latitude")
    @Mapping(target = "location.longitude" , source = "longitude")
    Customer toEntity(CustomerRequest customerRequest);

}
