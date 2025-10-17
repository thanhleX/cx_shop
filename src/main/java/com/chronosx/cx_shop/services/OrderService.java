package com.chronosx.cx_shop.services;

import java.util.List;

import com.chronosx.cx_shop.dtos.OrderDto;
import com.chronosx.cx_shop.dtos.responses.OrderResponse;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;

public interface OrderService {
    OrderResponse createOrder(OrderDto orderDto) throws DataNotFoundException;

    OrderResponse getOrderById(Long id);

    OrderResponse updateOrder(Long id, OrderDto orderDto);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders();
}
