package com.chronosx.cx_shop.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column(length = 100)
    String email;

    @Column(name = "phone_number", length = 10, nullable = false)
    String phoneNumber;

    @Column(length = 100)
    String address;

    @Column(length = 100)
    String note;

    @Column(name = "order_date")
    Date orderDate;

    String status;

    @Column(name = "total_money")
    Float totalMoney;

    @Column(name = "shipping_method", length = 100)
    String shippingMethod;

    @Column(name = "shipping_address", length = 200)
    String shippingAddress;

    @Column(name = "shipping_date")
    LocalDate shippingDate;

    @Column(name = "tracking_number", length = 100)
    String trackingNumber;

    @Column(name = "payment_method", length = 100)
    String paymentMethod;

    @Column(name = "is_active")
    Boolean isActive;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    List<OrderDetail> orderDetails;
}
