package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.BookDto;
import com.example.bookstoreapplication.dto.CreateBookRequestDto;
import com.example.bookstoreapplication.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto dto) {
        return bookService.save(dto);
    }
}
