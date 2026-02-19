package com.jumbotail.shippingchargeestimator.services.impl;

import com.jumbotail.shippingchargeestimator.exceptions.ProductNotFoundException;
import com.jumbotail.shippingchargeestimator.exceptions.SellerNotFoundException;
import com.jumbotail.shippingchargeestimator.mapper.ProductMapper;
import com.jumbotail.shippingchargeestimator.model.entity.Product;
import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import com.jumbotail.shippingchargeestimator.payloads.request.ProductRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.ProductResponse;
import com.jumbotail.shippingchargeestimator.repository.ProductRespository;
import com.jumbotail.shippingchargeestimator.repository.SellerRepository;
import com.jumbotail.shippingchargeestimator.services.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServices {

    private final ProductRespository productRespository;
    private final ProductMapper productMapper;
    private final SellerRepository sellerRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Seller seller = sellerRepository.findById(productRequest.getSellerId())
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with" + productRequest.getSellerId()));
        Product entity = productMapper.toEntity(productRequest);
        entity.setSeller(seller);
        Product save = productRespository.save(entity);
        return productMapper.toResponse(save);
    }

    @Override
    public Page<ProductResponse> getProductsBySellerId(long sellerId, Pageable pageable) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with" + sellerId));
        Page<Product> productBySellerId = productRespository.findProductBySeller_Id(seller.getId(), pageable);
        List<ProductResponse> list = productBySellerId.stream().map(productMapper::toResponse).toList();
        return new PageImpl<>(list, pageable, productBySellerId.getTotalElements());
    }

    @Override
    public ProductResponse getProductById(long id) {
        Product product = productRespository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not Found with id" + id));
        return productMapper.toResponse(product);
    }
}
