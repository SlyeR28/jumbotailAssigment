package com.jumbotail.shippingchargeestimator.service.impl;

import com.jumbotail.shippingchargeestimator.exceptions.WareHouseNotFoundException;
import com.jumbotail.shippingchargeestimator.mapper.WareHouseMapper;
import com.jumbotail.shippingchargeestimator.model.entity.Location;
import com.jumbotail.shippingchargeestimator.model.entity.Order;
import com.jumbotail.shippingchargeestimator.model.entity.Seller;
import com.jumbotail.shippingchargeestimator.model.entity.WareHouse;
import com.jumbotail.shippingchargeestimator.payloads.request.WarehouseRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import com.jumbotail.shippingchargeestimator.repository.OrderRepository;
import com.jumbotail.shippingchargeestimator.repository.SellerRepository;
import com.jumbotail.shippingchargeestimator.repository.WareHouseRepository;
import com.jumbotail.shippingchargeestimator.services.impl.WareHouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WareHouseServiceImplTest {

    @Mock
    private WareHouseRepository wareHouseRepository;

    @Mock
    private WareHouseMapper wareHouseMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private WareHouseServiceImpl wareHouseService;

    private WareHouse wareHouse;
    private WarehouseRequest warehouseRequest;
    private WarehouseResponse warehouseResponse;
    private Seller seller;
    private Order order;

    @BeforeEach
    void setUp() {
        wareHouse = new WareHouse();
        wareHouse.setId(1L);
        wareHouse.setLocation(new Location(12.9716, 77.5946)); // Bangalore

        warehouseRequest = new WarehouseRequest();

        warehouseResponse = new WarehouseResponse();
        warehouseResponse.setId(1L);
        warehouseResponse.setLatitude(12.9716);
        warehouseResponse.setLongitude(77.5946);

        seller = new Seller();
        seller.setId(1L);
        seller.setLocation(new Location(12.9352, 77.6245)); // Koramangala

        order = new Order();
        order.setId(1L);
    }

    @Test
    void createWarehouse_Success() {
        when(wareHouseMapper.toEntity(any(WarehouseRequest.class))).thenReturn(wareHouse);
        when(wareHouseRepository.save(any(WareHouse.class))).thenReturn(wareHouse);
        when(wareHouseMapper.toResponse(any(WareHouse.class))).thenReturn(warehouseResponse);

        WarehouseResponse response = wareHouseService.createWarehouse(warehouseRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(wareHouseRepository, times(1)).save(any(WareHouse.class));
    }

    @Test
    void getWarehouseById_Success() {
        when(wareHouseRepository.findById(1L)).thenReturn(Optional.of(wareHouse));
        when(wareHouseMapper.toResponse(any(WareHouse.class))).thenReturn(warehouseResponse);

        WarehouseResponse response = wareHouseService.getWarehouseById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getWarehouseById_NotFound() {
        when(wareHouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(WareHouseNotFoundException.class, () -> wareHouseService.getWarehouseById(1L));
    }

    @Test
    void findClosestWarehouse_Success() {
        WareHouse farWarehouse = new WareHouse();
        farWarehouse.setId(2L);
        farWarehouse.setLocation(new Location(28.7041, 77.1025)); // Delhi

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(wareHouseRepository.findAll()).thenReturn(Arrays.asList(wareHouse, farWarehouse));
        when(wareHouseMapper.toResponse(eq(wareHouse))).thenReturn(warehouseResponse);
        // We'll trust logic picks the closer one (Bangalore is closer to Koramangala
        // than Delhi)

        // Since we are mocking mapper, we need to ensure the mapper is called for the
        // winner or all?
        // The service maps all to response then finds min.
        WarehouseResponse farResponse = new WarehouseResponse();
        farResponse.setId(2L);
        when(wareHouseMapper.toResponse(eq(farWarehouse))).thenReturn(farResponse);

        WarehouseResponse response = wareHouseService.findClosestWarehouse(1L, 1L);

        assertNotNull(response);
        assertEquals(1L, response.getId()); // Should pick Bangalore
    }
}
