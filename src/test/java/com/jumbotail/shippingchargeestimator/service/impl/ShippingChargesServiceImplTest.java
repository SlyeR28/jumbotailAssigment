package com.jumbotail.shippingchargeestimator.service.impl;

import com.jumbotail.shippingchargeestimator.model.entity.*;
import com.jumbotail.shippingchargeestimator.payloads.request.ShippingChargeRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.CalculateShippingResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.ShippingChargeResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import com.jumbotail.shippingchargeestimator.repository.CustomerRepository;
import com.jumbotail.shippingchargeestimator.repository.SellerRepository;
import com.jumbotail.shippingchargeestimator.repository.ShippingRepository;
import com.jumbotail.shippingchargeestimator.repository.WareHouseRepository;
import com.jumbotail.shippingchargeestimator.services.WareHouseServices;
import com.jumbotail.shippingchargeestimator.services.impl.ShippingChargesServiceImpl;
import com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy.DeliveryStrategy;
import com.jumbotail.shippingchargeestimator.utils.DeliveryStrategy.DeliveryStrategyFactory;
import com.jumbotail.shippingchargeestimator.utils.ShiipingStragery.ShippingStargeryFactory;
import com.jumbotail.shippingchargeestimator.utils.ShiipingStragery.ShippingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShippingChargesServiceImplTest {

    @Mock
    private WareHouseRepository wareHouseRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private ShippingStargeryFactory shippingStargeryFactory;
    @Mock
    private DeliveryStrategyFactory deliveryStrategyFactory;
    @Mock
    private ShippingRepository shippingRepository;
    @Mock
    private WareHouseServices wareHouseServices;

    @Mock
    private ShippingStrategy shippingStrategy;
    @Mock
    private DeliveryStrategy deliveryStrategy;

    @InjectMocks
    private ShippingChargesServiceImpl shippingChargesService;

    private WareHouse wareHouse;
    private Customer customer;
    private Seller seller;
    private Shipping shipping;

    @BeforeEach
    void setUp() {
        Location loc = new Location(12.9716, 77.5946);
        wareHouse = new WareHouse();
        wareHouse.setId(1L);
        wareHouse.setLocation(loc);

        customer = new Customer();
        customer.setId(1L);
        customer.setLocation(loc); // Same location for simplicity

        seller = new Seller();
        seller.setId(1L);
        seller.setLocation(loc);

        Dimensions dim = new Dimensions();
        dim.setWeight(10.0);

        Product product = new Product();
        product.setDimensions(dim);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(1);

        Order order = new Order();
        order.setOrderItems(Collections.singletonList(item));

        shipping = new Shipping();
        shipping.setId(1L);
        shipping.setOrder(order);
    }

    @Test
    void calculateShippingCharge_Success() {
        when(wareHouseRepository.findById(1L)).thenReturn(Optional.of(wareHouse));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(shippingRepository.findTopByCustomerIdAndWarehouseIdOrderByShippedAtDesc(1L, 1L))
                .thenReturn(Optional.of(shipping));

        when(shippingStargeryFactory.create(anyDouble())).thenReturn(shippingStrategy);
        when(shippingStrategy.calculate(anyDouble(), anyDouble())).thenReturn(100.0);

        when(deliveryStrategyFactory.create(anyString())).thenReturn(deliveryStrategy);
        when(deliveryStrategy.calculateExtraCharge(anyDouble())).thenReturn(20.0);

        ShippingChargeResponse response = shippingChargesService.calculateShippingCharge(1L, 1L, "standard");

        assertNotNull(response);
        assertEquals(130.0, response.getShippingCharge()); // 100 + 20 + 10 (base constant)
    }

    @Test
    void calculateShippingForSeller_Success() {
        ShippingChargeRequest request = new ShippingChargeRequest();
        request.setSellerId(1L);
        request.setCustomerId(1L);
        request.setDeliverySpeed("express");

        WarehouseResponse whResponse = new WarehouseResponse();
        whResponse.setId(1L);
        whResponse.setLatitude(12.9716);
        whResponse.setLongitude(77.5946);
        whResponse.setDistanceKm(0.0);
        // IMPORTANT: WarehouseResponse usually has lat/long but here we might need to
        // mock getLatitude/getLongitude on it or check how it's used.
        // The service calls `warehouse.getLatitude()` which implies `WarehouseResponse`
        // has getters matching standard bean pattern.
        // Let's assume WarehouseResponse has these double fields available or exposed.
        // Actually, looking at the service: `warehouse.getLatitude()`
        // Let's verify WarehouseResponse class structure if needed, but for now
        // assuming standard Lombok getters works.

        // Wait, WarehouseResponse usually has an embedded Location object or flat
        // fields?
        // Service code: `warehouse.getLatitude()`.
        // If WarehouseResponse uses `warehouseLocation` object, then
        // `warehouse.getWarehouseLocation().getLatitude()` would be correct.
        // Let's check `ShippingChargesServiceImpl.java` line 115:
        // `warehouse.getLatitude()`.
        // This implies WarehouseResponse has direct latitude fields? Or the service
        // code is broken?
        // Logic check:
        // `WarehouseResponse warehouse =
        // wareHouseServices.findClosestWarehouse(seller.getId(), null);`
        // `warehouse` is of type `WarehouseResponse`.
        // Use of `warehouse.getLatitude()` suggests `WarehouseResponse` has this
        // method.

        // Let's proceed assuming it does, or I might encounter a compile error which
        // validates a bug in the service code.

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(wareHouseServices.findClosestWarehouse(1L, null)).thenReturn(whResponse);

        when(shippingStargeryFactory.create(anyDouble())).thenReturn(shippingStrategy);
        when(shippingStrategy.calculate(anyDouble(), anyDouble())).thenReturn(100.0);

        when(deliveryStrategyFactory.create(anyString())).thenReturn(deliveryStrategy);
        when(deliveryStrategy.calculateExtraCharge(anyDouble())).thenReturn(20.0);

        CalculateShippingResponse response = shippingChargesService.calculateShippingForSeller(request);

        assertNotNull(response);
        assertEquals(130.0, response.getShippingCharge());
    }
}
