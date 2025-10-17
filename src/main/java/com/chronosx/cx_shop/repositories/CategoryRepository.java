package com.chronosx.cx_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chronosx.cx_shop.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
