package com.example.bookstoreapplication.service.category;

import com.example.bookstoreapplication.dto.category.CategoryDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.CategoryMapper;
import com.example.bookstoreapplication.model.Category;
import com.example.bookstoreapplication.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Failed to get book by id=" + id)));
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        Category newCategory =  repository.save(mapper.toModel(dto));
        return mapper.toDto(newCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category newCategory = mapper.toModel(dto);
        newCategory.setId(id);
        return mapper.toDto(repository.save(newCategory));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
