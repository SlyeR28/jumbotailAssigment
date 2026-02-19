package com.jumbotail.shippingchargeestimator.services;

import com.jumbotail.shippingchargeestimator.payloads.request.SellerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.SellerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerService {

    // create Seller
    SellerResponse createSeller(SellerRequest sellerRequest);

    // get Seller By ID
    SellerResponse getSellerById(long id);

    // get All sellers
    Page<SellerResponse> getSellers(Pageable pageable);
}
