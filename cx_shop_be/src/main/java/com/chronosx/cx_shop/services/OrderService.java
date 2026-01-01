package com.chronosx.cx_shop.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chronosx.cx_shop.dtos.OrderDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.Order;
import com.chronosx.cx_shop.responses.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderDto orderDto) throws DataNotFoundException;

    OrderResponse getOrderById(Long id) throws DataNotFoundException;

    Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException;

    void deleteOrder(Long id);

    List<Order> getAllOrdersByUserId(Long userId);

    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);
}
