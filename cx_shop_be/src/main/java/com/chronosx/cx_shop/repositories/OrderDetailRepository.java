package com.chronosx.cx_shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronosx.cx_shop.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);
}
