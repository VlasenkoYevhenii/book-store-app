package com.example.bookstoreapplication.mapper;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setCoverImage(book.getCoverImage());
        return dto;
    }

    @Override
    public Book toModel(CreateBookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setCoverImage(dto.getCoverImage());
        return book;
    }
}
