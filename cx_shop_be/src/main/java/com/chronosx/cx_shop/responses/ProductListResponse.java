package com.chronosx.cx_shop.responses;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductListResponse {
    List<ProductResponse> products;
    int totalPages;
}
