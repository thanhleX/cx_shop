package com.chronosx.cx_shop.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDto {
    @JsonProperty("product_id")
    Long productId;

    @JsonProperty("quantity")
    Integer quantity;
}
