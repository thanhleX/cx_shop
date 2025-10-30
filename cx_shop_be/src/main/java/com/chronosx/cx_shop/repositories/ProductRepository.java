package com.chronosx.cx_shop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chronosx.cx_shop.models.Product;

import lombok.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    @Query("select a from Product a where "
            + "(:categoryId is null or :categoryId = 0 or a.category.id = :categoryId) "
            + "and (:keyword is null or :keyword = '' or a.name like %:keyword% or a.description like %:keyword%)")
    Page<Product> searchProducts(
            @Param("categoryId") Long categoryId, @Param("keyword") String keyword, Pageable pageable);
}
