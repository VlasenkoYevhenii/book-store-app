package com.example.bookstoreapplication.service.category;

import com.example.bookstoreapplication.dto.category.CategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);
    CategoryDto getById(Long id);
    CategoryDto save(CategoryDto dto);
    CategoryDto update(Long id, CategoryDto dto);
    void deleteById(Long id);
}
