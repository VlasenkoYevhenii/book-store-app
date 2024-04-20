package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.model.Book;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll();
}
