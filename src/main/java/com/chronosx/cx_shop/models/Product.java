package com.chronosx.cx_shop.models;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 350)
    String name;

    Float price;

    @Column(length = 300)
    String thumbnail;

    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
