package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookSearchParameters;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book management operations")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "List all books",
                description = "Returns a list of all books(uses pagination)")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get a book by ID", description = "Returns a book by specific ID")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new book to DB", description = "Adds a book to DB")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto dto) {
        return bookService.save(dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update a book", description = "Updates a book by its ID")
    public BookDto updateBook(@PathVariable Long id, @RequestBody CreateBookRequestDto dto) {
        return bookService.updateBook(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete by ID", description = "Deletes a book from DB")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Search book", description = "Searches a book by authors or its title")
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        return bookService.search(params, pageable);
    }
}
