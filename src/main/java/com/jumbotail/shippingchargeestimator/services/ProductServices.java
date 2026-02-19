package com.jumbotail.shippingchargeestimator.services;

import com.jumbotail.shippingchargeestimator.payloads.request.ProductRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServices {

    // creating products
    ProductResponse createProduct(ProductRequest productRequest);

    // getting product by seller id
    Page<ProductResponse> getProductsBySellerId(long sellerId , Pageable pageable);

    // get product by id
    ProductResponse getProductById(long id);


}
