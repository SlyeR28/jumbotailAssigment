package com.jumbotail.shippingchargeestimator.services.impl;

import com.jumbotail.shippingchargeestimator.exceptions.*;
import com.jumbotail.shippingchargeestimator.mapper.OrderMapper;
import com.jumbotail.shippingchargeestimator.mapper.WareHouseMapper;
import com.jumbotail.shippingchargeestimator.model.entity.*;
import com.jumbotail.shippingchargeestimator.model.enums.OrderStatus;
import com.jumbotail.shippingchargeestimator.model.enums.ShippingStatus;
import com.jumbotail.shippingchargeestimator.payloads.request.OrderItemRequest;
import com.jumbotail.shippingchargeestimator.payloads.request.OrderRequest;
import com.jumbotail.shippingchargeestimator.payloads.response.OrderResponse;
import com.jumbotail.shippingchargeestimator.payloads.response.WarehouseResponse;
import com.jumbotail.shippingchargeestimator.repository.*;
import com.jumbotail.shippingchargeestimator.services.OrderService;
import com.jumbotail.shippingchargeestimator.services.WareHouseServices;
import com.jumbotail.shippingchargeestimator.utils.LocationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRespository productRespository;
    private final WareHouseServices wareHouseServices;
    private final OrderMapper orderMapper;
    private final WareHouseMapper wareHouseMapper;
    private final WareHouseRepository wareHouseRepository;
    private final ShippingRepository shippingRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // fetch customer
        Customer customers = customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException("Customer Id is Required" + orderRequest.getCustomerId()));

        // fetch seller
        Seller seller = sellerRepository.findById(orderRequest.getSellerId()).orElseThrow(
                () -> new SellerNotFoundException("Seller Id is Required" + orderRequest.getSellerId()));

        // create Order
        Order order = Order.builder()
                .customers(customers)
                .seller(seller)
                .orderStatus(OrderStatus.CREATED)
                .build();

        double totalAmount = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        // create orderItems
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {

            Product product = productRespository.findById(itemRequest.getProductId()).orElseThrow(
                    () -> new ProductNotFoundException(" Product Id is Required" + itemRequest.getProductId()));

            double itemTotal = product.getPrice() * itemRequest.getQuantity();
            totalAmount += itemTotal;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setUpdatedAt(LocalDateTime.now());
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    @Override
    public OrderResponse confirmOrderBySeller(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with  id " + orderId));

        if (order.getOrderStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order Already processed");
        }

        // checking the stock if it full fill the customer need or not
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            if (product.getRemainingQuantity() < orderItem.getQuantity()) {
                order.setOrderStatus(OrderStatus.REJECTED_BY_SELLER);
                order.setUpdatedAt(LocalDateTime.now());
                orderRepository.save(order);
                System.out.println("Order rejected due to insufficient quantity");
                return orderMapper.toResponse(order);
            }
        }

        // reducing the stock and updating order status once
        List<Product> productsToUpdate = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setRemainingQuantity(product.getRemainingQuantity() - orderItem.getQuantity());
            productsToUpdate.add(product);
        }
        productRespository.saveAll(productsToUpdate);

        order.setOrderStatus(OrderStatus.PLACED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        /**
         * thinking about notification system also for now i am using sout
         * in future if we want to add notification service we can easily add
         */
        System.out.println("your order has been confirmed by Seller ! " + order.getId());
        return orderMapper.toResponse(order);

    }

    @Transactional
    @Override
    public OrderResponse assignWarehouse(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order id " + orderId));

        if (order.getOrderStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Order not confirmed by Seller yet !");
        }

        WarehouseResponse warehouse = wareHouseServices.findClosestWarehouse(order.getSeller().getId() , orderId);
        WareHouse wareHouseEntity = wareHouseRepository.findById(warehouse.getId()).orElseThrow(
                () -> new WareHouseNotFoundException("Warehouse with id Not Found" + warehouse.getId())
        );

        order.setWareHouse(wareHouseEntity);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Transactional
    @Override
    public OrderResponse markAsReachedWarehouse(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order id " + orderId));
        if (order.getOrderStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Order not assigned to WareHouse yet !");
        }
        order.setOrderStatus(OrderStatus.WAREHOUSE_ASSIGNED);
        order.setUpdatedAt(LocalDateTime.now());
        System.out.println("Order is successfully sent to WareHouse !" + order.getId());

        Order save = orderRepository.save(order);

        // Create Shipping record
        Shipping shipping = Shipping.builder()
                .order(order)
                .customer(order.getCustomers())
                .warehouse(order.getWareHouse()) // make sure warehouse is already assigned
                .status(ShippingStatus.ONGOING)
                .shippedAt(LocalDateTime.now())
                .distanceKm(LocationUtils.calculateDistance(
                        order.getWareHouse().getLocation().getLatitude(),
                        order.getWareHouse().getLocation().getLongitude(),
                        order.getCustomers().getLocation().getLatitude(),
                        order.getCustomers().getLocation().getLongitude()
                ))
                .build();

        shippingRepository.save(shipping);

        return orderMapper.toResponse(save);
    }
}
