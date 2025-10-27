package com.chronosx.cx_shop.responses;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse {
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
}
