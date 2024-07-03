package com.example.bookstoreapplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
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
    private static final Long VALID_ID = 1L;
    private static final int EXPECTED_ONE = 1;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;
    private static Pageable pageable;
    private static Category category;
    private static CategoryDto categoryDto;
    private static CategoryRequestDto categoryRequestDto;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        category = new Category();
        category.setId(VALID_ID);
        category.setName("Fiction");
        category.setDescription("Fictional books");

        categoryDto = new CategoryDto();
        categoryDto.setId(VALID_ID);
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName(category.getName());
        categoryRequestDto.setDescription(category.getDescription());
    }

    @Test
    @DisplayName("Verify findAll() method")
    void findAll_ValidPageable_ReturnCategoryDtoList() {
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(category)));
        when(categoryMapper.toDtoList(List.of(category))).thenReturn(List.of(categoryDto));

        List<CategoryDto> actual = categoryService.findAll(pageable);
        assertNotNull(actual);
        assertThat(actual).hasSize(EXPECTED_ONE);
        assertThat(actual).contains(categoryDto);

        verify(categoryRepository, atMostOnce()).findAll(pageable);
        verify(categoryMapper, atMostOnce()).toDtoList(List.of(category));
    }

    @Test
    @DisplayName("Verify getById() method with valid id")
    void getById_ValidId_ReturnCategoryDto() {
        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.getById(VALID_ID);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(categoryDto);

        verify(categoryRepository, atMostOnce()).findById(VALID_ID);
        verify(categoryMapper, atMostOnce()).toDto(category);
    }

    @Test
    @DisplayName("Verify getById() method with invalid id")
    void getById_InvalidId_ThrowEntityNotFoundException() {
        when(categoryRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> categoryService.getById(VALID_ID));
        assertThat(exception.getMessage()).isEqualTo("Failed to get book by id=1");

        verify(categoryRepository, atMostOnce()).findById(VALID_ID);
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

        verify(categoryMapper, atMostOnce()).toModel(categoryRequestDto);
        verify(categoryRepository, atMostOnce()).save(category);
        verify(categoryMapper, atMostOnce()).toDto(category);
    }

    @Test
    @DisplayName("Verify update() method")
    void update_ValidCategoryRequestDto_ReturnCategoryDto() {
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.update(VALID_ID, categoryRequestDto);
        assertNotNull(actual);
        assertThat(actual).isEqualTo(categoryDto);

        verify(categoryMapper, atMostOnce()).toModel(categoryRequestDto);
        verify(categoryRepository, atMostOnce()).save(category);
        verify(categoryMapper, atMostOnce()).toDto(category);
    }

    @Test
    @DisplayName("Verify deleteById() method")
    void deleteById_ValidId() {
        doNothing().when(categoryRepository).deleteById(VALID_ID);

        categoryService.deleteById(VALID_ID);

        verify(categoryRepository, atMostOnce()).deleteById(VALID_ID);
    }
}
