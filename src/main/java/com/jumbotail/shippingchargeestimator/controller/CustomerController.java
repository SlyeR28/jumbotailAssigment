package com.jumbotail.shippingchargeestimator.controller;

import com.jumbotail.shippingchargeestimator.payloads.request.CustomerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CustomerResponse;
import com.jumbotail.shippingchargeestimator.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        return  new ResponseEntity<>(customerService.createCustomer(customerRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CustomerResponse>> findAllCustomers(Pageable pageable){
        return new ResponseEntity<>(customerService.getAllCustomers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable long id){
       return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }
}
