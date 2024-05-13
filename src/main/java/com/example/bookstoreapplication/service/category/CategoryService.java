package com.example.bookstoreapplication.service.category;

import com.example.bookstoreapplication.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto getById(Long id);
    CategoryDto save(CategoryDto dto);
    CategoryDto update(Long id, CategoryDto dto);
    void deleteById(Long id);
}
