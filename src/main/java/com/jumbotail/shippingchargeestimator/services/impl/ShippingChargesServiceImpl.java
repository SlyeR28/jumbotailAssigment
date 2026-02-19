package com.jumbotail.shippingchargeestimator.services.impl;

import com.jumbotail.shippingchargeestimator.exceptions.CustomerNotFoundException;
import com.jumbotail.shippingchargeestimator.exceptions.SellerNotFoundException;
import com.jumbotail.shippingchargeestimator.exceptions.WareHouseNotFoundException;
import com.jumbotail.shippingchargeestimator.model.entity.Customer;
import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import com.jumbotail.shippingchargeestimator.model.entity.Shipping;
import com.jumbotail.shippingchargeestimator.model.entity.WareHouse;
import com.jumbotail.shippingchargeestimator.model.enums.ShippingStatus;
import com.jumbotail.shippingchargeestimator.payloads.request.ShippingChargeRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CalculateShippingResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.ShippingChargeResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import com.jumbotail.shippingchargeestimator.repository.CustomerRepository;
import com.jumbotail.shippingchargeestimator.repository.SellerRepository;
import com.jumbotail.shippingchargeestimator.repository.ShippingRepository;
import com.jumbotail.shippingchargeestimator.repository.WareHouseRepository;
import com.jumbotail.shippingchargeestimator.services.ShippingChargesService;
import com.jumbotail.shippingchargeestimator.services.WareHouseServices;
import com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy.DeliveryStrategy;
import com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy.DeliveryStrategyFactory;
import com.jumbotail.shippingchargeestimator.utils.LocationUtils;
import com.jumbotail.shippingchargeestimator.utils.ShiipingStragery.ShippingStargeryFactory;
import com.jumbotail.shippingchargeestimator.utils.ShiipingStragery.ShippingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShippingChargesServiceImpl implements ShippingChargesService {

    private final WareHouseRepository wareHouseRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ShippingStargeryFactory shippingStargeryFactory;
    private final DeliveryStrategyFactory deliveryStrategyFactory;
    private final ShippingRepository  shippingRepository;
    private WareHouseServices wareHouseServices;

    @Override
    public ShippingChargeResponse calculateShippingCharge(Long warehouseId, Long customerId,String deliverySpeed) {
        WareHouse wareHouse = wareHouseRepository.findById(warehouseId).orElseThrow(
                () -> new WareHouseNotFoundException("Warehouse not found with id: " + warehouseId));

        Customer customers = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        Shipping shipping = shippingRepository.
                findTopByCustomerIdAndWarehouseIdOrderByShippedAtDesc(customerId , warehouseId)
                .orElseThrow(() -> new RuntimeException("No ongoing shipping found for this customer and warehouse"));


        // finding the distance so that we can easily find which stratergy used for pricing
        double distance = LocationUtils.calculateDistance(
                wareHouse.getLocation().getLatitude(),
                wareHouse.getLocation().getLongitude(),
                customers.getLocation().getLatitude(),
                customers.getLocation().getLongitude()
        );

        // calculating the weight -> to get shipping price from warehouse to customer location
        double totalWeight = shipping.getOrder().getOrderItems()
                .stream()
                .mapToDouble(item -> item.getProduct().getDimensions().getWeight() * item.getQuantity())
                .sum();

        ShippingStrategy strategy = shippingStargeryFactory.create(distance);
        // by this we are calculating the base of shipping in terms of distance and weight
        double calculated = strategy.calculate(distance, totalWeight);

        // now we add shipping price according to deleviry speed
        DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(deliverySpeed);
        double extraCharge = deliveryStrategy.calculateExtraCharge(totalWeight);

        // final shipping Price
        double shippingCharge = calculated + extraCharge + 10;

        shipping.setDistanceKm(distance);
        shipping.setShippingCharge(shippingCharge);
        shipping.setStatus(ShippingStatus.ONGOING);
        shipping.setShippedAt(LocalDateTime.now());
        shippingRepository.save(shipping);



        return ShippingChargeResponse.builder()
                .id(shipping.getId())
                .customerId(customerId)
                .warehouseId(warehouseId)
                .distanceKm(distance)
                .shippingCharge(shippingCharge)
                .deliverySpeed(deliverySpeed)
                .build();
    }

    @Override
    public CalculateShippingResponse calculateShippingForSeller(ShippingChargeRequest shippingCharge) {

        // fetching seller and customer from database

        Seller seller = sellerRepository.findById(shippingCharge.getSellerId()).orElseThrow(
                () -> new SellerNotFoundException("Seller not found with id: " + shippingCharge.getSellerId())
        );

        Customer customer = customerRepository.findById(shippingCharge.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with id: " + shippingCharge.getCustomerId()));

        // finding nearest warehouse to the seller
        WarehouseResponse warehouse = wareHouseServices.findClosestWarehouse(seller.getId(), null);

        double distance = LocationUtils.calculateDistance(
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                customer.getLocation().getLatitude(),
                customer.getLocation().getLongitude()
        );

        //Assuming that default weight is 10 b/c there is no order
        double weight = 10.0;

        // choosing strategy on location basis
        ShippingStrategy strategy = shippingStargeryFactory.create(distance);
        double calculated = strategy.calculate(distance, weight);

        // applying the delivery strategy
        DeliveryStrategy deliveryStrategy = deliveryStrategyFactory.create(shippingCharge.getDeliverySpeed());
        double extraCharge = deliveryStrategy.calculateExtraCharge(weight);

        // total shipping charges with assuming 10 kg weight
        double charges = calculated + extraCharge + 10;

        return CalculateShippingResponse.builder()
                .shippingCharge(charges)
                .nearestWarehouse(warehouse)
                .build();
    }
}
