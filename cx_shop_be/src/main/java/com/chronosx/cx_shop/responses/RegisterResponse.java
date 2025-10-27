package com.chronosx.cx_shop.responses;

import com.chronosx.cx_shop.models.User;
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
public class RegisterResponse {
    @JsonProperty("message")
    String message;

    @JsonProperty("user")
    User user;
}
