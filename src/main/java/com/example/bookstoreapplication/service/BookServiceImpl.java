package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.BookRepository;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper;

    @Override
    public BookDto save(CreateBookRequestDto dto) {
        Book book = mapper.toModel(dto);
        bookRepository.save(book);
        return mapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
