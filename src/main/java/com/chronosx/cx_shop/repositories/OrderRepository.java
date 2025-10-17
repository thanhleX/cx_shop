package com.chronosx.cx_shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronosx.cx_shop.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
