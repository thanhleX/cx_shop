package com.chronosx.cx_shop.services;

import java.util.List;

import com.chronosx.cx_shop.dtos.OrderDetailDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.OrderDetail;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws DataNotFoundException;

    OrderDetail getOrderDetailById(Long id) throws DataNotFoundException;

    void deleteOrderDetail(Long id);

    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) throws DataNotFoundException;

    List<OrderDetail> getOrderDetails(Long orderId);
}
