package com.chronosx.cx_shop.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @JsonProperty("full_name")
    String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number can't be empty")
    String phoneNumber;

    String address;

    @NotBlank(message = "Email can't be empty")
    String password;

    @JsonProperty("retype_password")
    String retypePassword;

    @JsonProperty("date_of_birth")
    String dateOfBirth;

    @JsonProperty("facebook_account_id")
    int facebookAccountId;

    @JsonProperty("google_account_id")
    int googleAccountId;

    @JsonProperty("role_id")
    @NotNull(message = "Role can't be empty")
    Long roleId;
}
