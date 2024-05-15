package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.category.CategoryDto;
import com.example.bookstoreapplication.dto.category.CategoryRequestDto;
import com.example.bookstoreapplication.model.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categoryList);

    Category toModel(CategoryRequestDto dto);
}
