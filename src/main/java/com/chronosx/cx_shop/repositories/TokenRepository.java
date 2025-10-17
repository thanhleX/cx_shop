package com.chronosx.cx_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronosx.cx_shop.models.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {}
