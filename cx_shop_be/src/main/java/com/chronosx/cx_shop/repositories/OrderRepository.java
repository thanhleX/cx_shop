package com.chronosx.cx_shop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chronosx.cx_shop.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query(
            """
		select o from Order o
		where (:keyword is null or :keyword = '' or o.fullName like %:keyword% or o.address like %:keyword%
		or o.note like %:keyword%)
		""")
    Page<Order> findByKeyword(String keyword, Pageable pageable);
}
