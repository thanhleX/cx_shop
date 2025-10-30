package com.chronosx.cx_shop.dtos;

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
public class UserLoginDto {
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number can't be empty")
    String phoneNumber;

    @NotBlank(message = "Email can't be empty")
    String password;

    @Min(value = 1, message = "You must enter role's Id")
    @JsonProperty("role_id")
    Long roleId;
}
