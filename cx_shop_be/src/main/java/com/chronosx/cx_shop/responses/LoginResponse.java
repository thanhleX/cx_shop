package com.chronosx.cx_shop.responses;

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
public class LoginResponse {
    @JsonProperty("message")
    String message;

    @JsonProperty("token")
    String token;
}
