package com.example.bookstoreapplication.service.category;

import com.example.bookstoreapplication.dto.category.CategoryDto;
import com.example.bookstoreapplication.dto.category.CategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto dto);

    CategoryDto update(Long id, CategoryRequestDto dto);

    void deleteById(Long id);
}
