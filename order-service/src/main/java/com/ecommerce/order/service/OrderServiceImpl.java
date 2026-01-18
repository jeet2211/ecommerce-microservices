package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.event.Order;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Order order = orderMapper.toEntity(orderRequest);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatus("CREATED");

        return orderMapper.toResponse(orderRepository.save(order));
    }
}
