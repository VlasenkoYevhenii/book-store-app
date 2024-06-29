package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(target = "id", source = "book.id")
    BookDto toDto(Book book);

    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoriesById")
    Book toModel(CreateBookRequestDto requestDto);

    Book toModelFromResponse(BookDto dto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }

    @Named("categoriesById")
    default Set<Category> categoriesByIds(Set<Long> categories) {
        return categories.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }
}
