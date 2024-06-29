package com.example.bookstoreapplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstoreapplication.dto.category.CategoryDto;
import com.example.bookstoreapplication.dto.category.CategoryRequestDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.CategoryMapper;
import com.example.bookstoreapplication.model.Category;
import com.example.bookstoreapplication.repository.category.CategoryRepository;
import com.example.bookstoreapplication.service.category.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;
    private CategoryRequestDto categoryRequestDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Fiction");
        category.setDescription("Fictional books");

        categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Fiction");
        categoryDto.setDescription("Fictional books");

        categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Fiction");
        categoryRequestDto.setDescription("Fictional books");
    }

    @Test
    @DisplayName("Verify findAll() method")
    void findAll_ValidPageable_ReturnCategoryDtoList() {
        Pageable pageable = PageRequest.of(0, 10);
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(category)));
        when(categoryMapper.toDtoList(List.of(category))).thenReturn(List.of(categoryDto));

        List<CategoryDto> actual = categoryService.findAll(pageable);
        assertNotNull(actual);
        assertThat(actual).hasSize(1);
        assertThat(actual).contains(categoryDto);

        verify(categoryRepository, times(1)).findAll(pageable);
        verify(categoryMapper, times(1)).toDtoList(List.of(category));
    }

    @Test
    @DisplayName("Verify getById() method with valid id")
    void getById_ValidId_ReturnCategoryDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.getById(1L);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(categoryDto);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    @DisplayName("Verify getById() method with invalid id")
    void getById_InvalidId_ThrowEntityNotFoundException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                            () -> categoryService.getById(1L));
        assertThat(exception.getMessage()).isEqualTo("Failed to get book by id=1");

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("Verify save() method")
    void save_ValidCategoryRequestDto_ReturnCategoryDto() {
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.save(categoryRequestDto);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(categoryDto);

        verify(categoryMapper, times(1)).toModel(categoryRequestDto);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    @DisplayName("Verify update() method")
    void update_ValidCategoryRequestDto_ReturnCategoryDto() {
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.update(1L, categoryRequestDto);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(categoryDto);

        verify(categoryMapper, times(1)).toModel(categoryRequestDto);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    @DisplayName("Verify deleteById() method")
    void deleteById_ValidId() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
