package com.jumbotail.shippingchargeestimator.services;

import com.jumbotail.shippingchargeestimator.payloads.request.CustomerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    // creating customer
    CustomerResponse createCustomer(CustomerRequest customerRequest);

     // getting all customers
    Page<CustomerResponse> getAllCustomers(Pageable pageable);

    // get customer by Id
    CustomerResponse getCustomerById(long id);

}
