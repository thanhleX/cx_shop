package com.chronosx.cx_shop.services.implement;

import static com.chronosx.cx_shop.models.ProductImage.MAXIMUM_IMAGES_PER_PRODUCT;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chronosx.cx_shop.dtos.ProductDto;
import com.chronosx.cx_shop.dtos.ProductImageDto;
import com.chronosx.cx_shop.dtos.responses.ProductResponse;
import com.chronosx.cx_shop.exceptions.DataNotFoundException;
import com.chronosx.cx_shop.exceptions.InvalidParamException;
import com.chronosx.cx_shop.models.Category;
import com.chronosx.cx_shop.models.Product;
import com.chronosx.cx_shop.models.ProductImage;
import com.chronosx.cx_shop.repositories.CategoryRepository;
import com.chronosx.cx_shop.repositories.ProductImageRepository;
import com.chronosx.cx_shop.repositories.ProductRepository;
import com.chronosx.cx_shop.services.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductImageRepository productImageRepository;

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("product id not found"));
    }

    @Override
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        Category category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("category id not found"));

        Product newProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .description(productDto.getDescription())
                .category(category)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDto) throws DataNotFoundException {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            Category category = categoryRepository
                    .findById(productDto.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException("category id not found"));

            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setCategory(category);
            existingProduct.setThumbnail(productDto.getThumbnail());
            return productRepository.save(existingProduct);
        }

        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException("product id not found"));

        ProductImage productImage = ProductImage.builder()
                .product(product)
                .imageUrl(productImageDto.getImageUrl())
                .build();

        int size = productImageRepository.findByProductId(productId).size();
        if (size >= MAXIMUM_IMAGES_PER_PRODUCT)
            throw new InvalidParamException("number of images must be <= " + MAXIMUM_IMAGES_PER_PRODUCT);

        return productImageRepository.save(productImage);
    }
}
