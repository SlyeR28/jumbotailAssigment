package com.jumbotail.shippingchargeestimator.services.impl;

import com.jumbotail.shippingchargeestimator.exceptions.SellerNotFoundException;
import com.jumbotail.shippingchargeestimator.mapper.SellerMapper;
import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import com.jumbotail.shippingchargeestimator.payloads.request.SellerRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.SellerResponse;
import com.jumbotail.shippingchargeestimator.repository.SellerRepository;
import com.jumbotail.shippingchargeestimator.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public SellerResponse createSeller(SellerRequest sellerRequest) {
        Seller entity = sellerMapper.toEntity(sellerRequest);
        Seller save = sellerRepository.save(entity);
        return sellerMapper.toResponse(save);
    }

    @Override
    public SellerResponse getSellerById(long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller Not Found by id" + id));
        return sellerMapper.toResponse(seller);
    }

    @Override
    public Page<SellerResponse> getSellers(Pageable pageable) {
        Page<Seller> all = sellerRepository.findAll(pageable);
        List<SellerResponse> list =
                all.stream().map(sellerMapper::toResponse).toList();
         return new PageImpl<>(list, pageable, all.getTotalElements());
    }
}
