package com.chronosx.cx_shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronosx.cx_shop.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
