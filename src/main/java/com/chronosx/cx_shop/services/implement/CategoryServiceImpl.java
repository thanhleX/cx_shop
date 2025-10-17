package com.chronosx.cx_shop.services.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chronosx.cx_shop.dtos.CategoryDto;
import com.chronosx.cx_shop.models.Category;
import com.chronosx.cx_shop.repositories.CategoryRepository;
import com.chronosx.cx_shop.services.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = Category.builder().name(categoryDto.getName()).build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(categoryDto.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
