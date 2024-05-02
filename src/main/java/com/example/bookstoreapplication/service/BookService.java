package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.BookSearchParameters;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto dto);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters params);
}
