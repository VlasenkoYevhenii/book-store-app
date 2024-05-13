package com.example.bookstoreapplication.service.book;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.BookSearchParameters;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto dto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto updateBook(Long id, CreateBookRequestDto dto);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters params);

    List<BookDtoWithoutCategoryIds> getBookDtosByCategoryId(Long id, Pageable pageable);
}
