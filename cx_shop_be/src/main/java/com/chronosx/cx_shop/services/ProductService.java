package com.chronosx.cx_shop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.chronosx.cx_shop.dtos.ProductDto;
import com.chronosx.cx_shop.dtos.ProductImageDto;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.models.Product;
import com.chronosx.cx_shop.models.ProductImage;
import com.chronosx.cx_shop.responses.ProductResponse;

public interface ProductService {
    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);

    Product getProductById(Long id) throws DataNotFoundException;

    Product createProduct(ProductDto productDto) throws DataNotFoundException;

    Product updateProduct(Long id, ProductDto productDto) throws DataNotFoundException;

    void deleteProduct(Long id);

    boolean existsByName(String name);

    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception;
}
