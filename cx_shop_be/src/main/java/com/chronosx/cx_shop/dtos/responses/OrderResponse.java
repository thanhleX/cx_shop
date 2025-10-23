package com.chronosx.cx_shop.dtos.responses;

import java.time.LocalDate;
import java.util.Date;

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
public class OrderResponse extends BaseResponse {
    Long id;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("full_name")
    String fullName;

    @JsonProperty("phone_number")
    String phoneNumber;

    String address;
    String note;

    @JsonProperty("order_date")
    Date orderDate;

    String status;

    @JsonProperty("total_money")
    Float totalMoney;

    @JsonProperty("shipping_method")
    String shippingMethod;

    @JsonProperty("shipping_address")
    String shippingAddress;

    @JsonProperty("shipping_date")
    LocalDate shippingDate;

    @JsonProperty("tracking_number")
    String trackingNumber;

    @JsonProperty("payment_method")
    String paymentMethod;

    @JsonProperty("is_active")
    Boolean isActive;
}
