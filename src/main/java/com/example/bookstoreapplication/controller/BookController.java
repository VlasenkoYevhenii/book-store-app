package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    public BookDto getBookById(long id) {
        return null;
    }

    public BookDto createBook(CreateBookRequestDto dto) {
        return null;
    }
}
