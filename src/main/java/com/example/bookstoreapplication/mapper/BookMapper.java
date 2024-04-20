package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.model.Book;

public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto dto);
}
