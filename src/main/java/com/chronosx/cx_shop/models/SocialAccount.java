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
@Table(name = "social_accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 20, nullable = false)
    String provider;

    @Column(name = "provider_id", length = 50, nullable = false)
    String providerId;

    @Column(length = 150)
    String email;

    @Column(length = 100)
    String name;

    //    @ManyToOne
    //    @JoinColumn(name = "user_id")
    //    User user;
}
