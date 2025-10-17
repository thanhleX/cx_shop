package com.chronosx.cx_shop.services;

import java.util.List;

import com.chronosx.cx_shop.dtos.CategoryDto;
import com.chronosx.cx_shop.models.Category;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(CategoryDto category);

    Category updateCategory(Long id, CategoryDto category);

    void deleteCategory(Long id);
}
