package com.chronosx.cx_shop.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageDto {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    Long productId;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image URL must be between 5 and 200 characters")
    String imageUrl;
}
