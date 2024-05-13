package com.example.bookstoreapplication.service.book;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.BookSearchParameters;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.book.BookRepository;
import com.example.bookstoreapplication.repository.book.BookSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final BookSpecificationBuilder specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto dto) {
        Book book = mapper.toModel(dto);
        return mapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        return mapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Failed to get book by id=" + id)));
    }

    @Override
    public BookDto updateBook(Long id, CreateBookRequestDto dto) {
        Book book = mapper.toModel(dto);
        book.setId(id);
        return mapper.toDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params) {
        Specification<Book> spec = specificationBuilder.build(params);
        return bookRepository
                .findAll(spec)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBookDtosByCategoryId(Long id, Pageable pageable) {
        return bookRepository
                .findByCategoriesId(id)
                .stream()
                .map(mapper::toDtoWithoutCategoryIds)
                .toList();
    }}
