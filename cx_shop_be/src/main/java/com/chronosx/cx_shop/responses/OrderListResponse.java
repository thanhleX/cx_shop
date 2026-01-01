package com.chronosx.cx_shop.responses;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderListResponse {
    List<OrderResponse> orders;
    int totalPages;
}
