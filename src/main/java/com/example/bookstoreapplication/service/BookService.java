package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll();

    BookDto getById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto dto);

    void delete(Long id);
}
