package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.config.MapperConfig;
import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto dto);
}
