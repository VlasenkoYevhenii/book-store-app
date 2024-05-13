package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto dto);

    BookDtoWithoutCategoryIds toDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto dto, Book book) {
        Set<Category> bookCategories = book.getCategories();
        for (Category category : bookCategories) {
            dto.getCategoriesIds().add(category.getId());
        }
    }
}
