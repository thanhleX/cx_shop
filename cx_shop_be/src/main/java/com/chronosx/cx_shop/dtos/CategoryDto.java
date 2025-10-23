package com.chronosx.cx_shop.dtos;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    @NotEmpty(message = "Name can't be empty")
    String name;
}
