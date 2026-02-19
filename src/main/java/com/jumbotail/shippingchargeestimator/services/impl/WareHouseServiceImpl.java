package com.jumbotail.shippingchargeestimator.services.impl;

import com.jumbotail.shippingchargeestimator.exceptions.OrderNotFoundException;
import com.jumbotail.shippingchargeestimator.exceptions.SellerNotFoundException;
import com.jumbotail.shippingchargeestimator.exceptions.WareHouseNotFoundException;
import com.jumbotail.shippingchargeestimator.mapper.WareHouseMapper;
import com.jumbotail.shippingchargeestimator.model.entity.Order;
import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import com.jumbotail.shippingchargeestimator.model.entity.WareHouse;
import com.jumbotail.shippingchargeestimator.payloads.request.WarehouseRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import com.jumbotail.shippingchargeestimator.repository.OrderRepository;
import com.jumbotail.shippingchargeestimator.repository.SellerRepository;
import com.jumbotail.shippingchargeestimator.repository.WareHouseRepository;
import com.jumbotail.shippingchargeestimator.services.WareHouseServices;
import com.jumbotail.shippingchargeestimator.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class WareHouseServiceImpl implements WareHouseServices {

    private final WareHouseRepository wareHouseRepository;
    private final WareHouseMapper  wareHouseMapper;
    private final OrderRepository orderRepository;
    private final SellerRepository sellerRepository;


    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest warehouseRequest) {
        WareHouse entity = wareHouseMapper.toEntity(warehouseRequest);
        WareHouse warehouse = wareHouseRepository.save(entity);
        return wareHouseMapper.toResponse(warehouse);
    }


    @Override
    @Cacheable(value = "warehouses" , key = "#id")
    public WarehouseResponse getWarehouseById(Long id) {
        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WareHouseNotFoundException("WareHouse Not Found By Id" + id));
        return wareHouseMapper.toResponse(wareHouse);
    }

    @Override
    @Cacheable(value = "nearestWarehouse" , key = "#sellerId")
    public WarehouseResponse findClosestWarehouse(Long sellerId , Long orderId) {

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() ->
                        new SellerNotFoundException("Seller Not Found By Id " + sellerId));

         orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found By Id " + orderId)
        );

        return wareHouseRepository.findAll()
                .stream()
                .map(wareHouse -> {
                    double distance = LocationUtils.calculateDistance(
                            seller.getLocation().getLatitude(),
                            seller.getLocation().getLongitude(),
                            wareHouse.getLocation().getLatitude(),
                            wareHouse.getLocation().getLongitude()
                    );

                    WarehouseResponse response = wareHouseMapper.toResponse(wareHouse);
                    response.setDistanceKm(distance);
                    return response;
                })
                .min(Comparator.comparingDouble(WarehouseResponse::getDistanceKm))
                .orElseThrow(() ->
                        new WareHouseNotFoundException("No warehouses found in the system"));
    }

}
