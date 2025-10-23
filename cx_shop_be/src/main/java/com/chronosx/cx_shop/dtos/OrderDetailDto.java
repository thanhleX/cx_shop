package com.chronosx.cx_shop.dtos;

import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDto {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order id must be greater than 0")
    Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    Long productId;

    @Min(value = 0, message = "Quantity must be greater than 0")
    float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Number of products must be greater than 0")
    int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    Float totalMoney;

    String color;
}
