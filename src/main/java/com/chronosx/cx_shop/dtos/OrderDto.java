package com.chronosx.cx_shop.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User id must be greater than 0")
    Long userId;

    @JsonProperty("full_name")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number can't be empty")
    String phoneNumber;

    String address;
    String note;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    Float totalMoney;

    @JsonProperty("shipping_method")
    String shippingMethod;

    @JsonProperty("shipping_address")
    String shippingAddress;

    @JsonProperty("shipping_date")
    LocalDate shippingDate;

    @JsonProperty("payment_method")
    String paymentMethod;
}
