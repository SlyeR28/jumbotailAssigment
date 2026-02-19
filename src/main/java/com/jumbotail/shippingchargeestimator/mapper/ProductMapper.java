package com.jumbotail.shippingchargeestimator.mapper;

import com.jumbotail.shippingchargeestimator.model.entity.Product;
import com.jumbotail.shippingchargeestimator.payloads.request.ProductRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "weight" , source = "dimensions.weight")
    @Mapping(target = "length"  , source = "dimensions.length")
    @Mapping(target = "height" , source = "dimensions.height")
    @Mapping(target = "width" , source = "dimensions.width")
    @Mapping(target = "sellerId" , source = "seller.id")
    @Mapping(target = "inStock" , source = "inStock")
    ProductResponse toResponse(Product product);


    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "seller" , ignore = true)
    @Mapping(target = "dimensions.weight" , source = "weight")
    @Mapping(target = "dimensions.length" , source = "length")
    @Mapping(target = "dimensions.height", source = "height")
    @Mapping(target = "dimensions.width" , source = "width")
    Product toEntity(ProductRequest productRequest);

}
