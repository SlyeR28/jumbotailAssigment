package com.jumbotail.shippingchargeestimator.services.impl;

import com.jumbotail.shippingchargeestimator.exceptions.CustomerNotFoundException;
import com.jumbotail.shippingchargeestimator.mapper.CustomerMapper;

import com.jumbotail.shippingchargeestimator.model.entity.Customer;
import com.jumbotail.shippingchargeestimator.payloads.request.CustomerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CustomerResponse;
import com.jumbotail.shippingchargeestimator.repository.CustomerRepository;
import com.jumbotail.shippingchargeestimator.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServicesImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper  customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer entity = customerMapper.toEntity(customerRequest);
        entity = customerRepository.save(entity);
        return customerMapper.toResponse(entity);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
      // Fetch paginated customers from the repository
        Page<Customer> all = customerRepository.findAll(pageable);

        //convert entity to DTOS using mapper
        List<CustomerResponse> list = all.stream().map(customerMapper::toResponse).toList();
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    @Override
    public CustomerResponse getCustomerById(long id) {
        Customer customers = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
        return customerMapper.toResponse(customers);
    }
}
