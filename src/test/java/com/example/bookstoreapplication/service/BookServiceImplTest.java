package com.example.bookstoreapplication.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.book.BookRepository;
import com.example.bookstoreapplication.service.book.BookServiceImpl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final Long VALID_ID = 1L;
    private static final int VALID_PRICE = 100;
    private static final int EXPECTED_TWO = 2;
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;

    private static Pageable pageable;
    private static Book testBook;
    private static Book testBook2;
    private static CreateBookRequestDto testRequestDto;
    private static BookDto testResponseDto;
    private static BookDto testResponseDto2;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        testRequestDto = new CreateBookRequestDto()
                .setTitle("Test book")
                .setAuthor("Test Author")
                .setPrice(BigDecimal.valueOf(VALID_PRICE))
                .setIsbn("1234567891234");
        testBook = new Book()
                .setTitle(testRequestDto.getTitle())
                .setAuthor(testRequestDto.getAuthor())
                .setPrice(testRequestDto.getPrice())
                .setIsbn(testRequestDto.getIsbn());
        testResponseDto = new BookDto()
                .setTitle(testBook.getTitle())
                .setAuthor(testBook.getAuthor())
                .setPrice(testBook.getPrice())
                .setIsbn(testBook.getIsbn());
        testBook2 = new Book()
                .setTitle("Another test book")
                .setAuthor("Another test author")
                .setPrice(BigDecimal.valueOf(VALID_PRICE))
                .setIsbn("1234567899874");
        testResponseDto2 = new BookDto()
                .setTitle(testBook2.getTitle())
                .setAuthor(testBook2.getAuthor())
                .setPrice(testBook2.getPrice())
                .setIsbn(testBook2.getIsbn());
    }

    @Test
    @DisplayName("""
            Test the save() method\s
            should return a BookDto""")
    void save_AllValidConditions_ReturnsBookDto() {
        mockMapperToModelAndSave();
        when(bookMapper.toDto(testBook)).thenReturn(testResponseDto);

        BookDto actual = bookService.save(testRequestDto);

        assertNotNull(actual);
        assertEquals(testResponseDto, actual);
        verifyInteractionsForSave();
    }

    @Test
    @DisplayName("""
            Test the getById() method\s
            should return a BookDto""")
    void getById_AllValidConditions_ReturnsBookDto() {
        testBook.setId(VALID_ID);
        when(bookRepository.findById(VALID_ID)).thenReturn(Optional.of(testBook));
        when(bookMapper.toDto(testBook)).thenReturn(testResponseDto);

        BookDto actual = bookService.getById(VALID_ID);

        assertNotNull(actual);
        assertEquals(testResponseDto, actual);
        verify(bookRepository, atMostOnce()).findById(VALID_ID);
        verify(bookMapper, atMostOnce()).toDto(testBook);
    }

    @Test
    @DisplayName("""
            Test the updateBook() method\s
            should return a BookDto""")
    public void updateBook_AllValidConditions_ReturnsBookDto() {
        mockMapperToModelAndSave();
        when(bookMapper.toDto(testBook)).thenReturn(testResponseDto);

        BookDto actual = bookService.updateBook(VALID_ID, testRequestDto);

        assertNotNull(actual);
        assertEquals(testResponseDto, actual);
        verifyInteractionsForSave();
    }

    @Test
    @DisplayName("""
            Test the findAll() method\s
            should return a page of BookDto""")
    public void findAll_AllValidConditions_ReturnsListOfBookDto() {
        when(bookRepository.findAll(pageable)).thenReturn(
                new PageImpl<>(Arrays.asList(testBook, testBook2), pageable, EXPECTED_TWO));
        when(bookMapper.toDto(testBook)).thenReturn(testResponseDto);
        when(bookMapper.toDto(testBook2)).thenReturn(testResponseDto2);

        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertNotNull(actualBookDtos);
        assertEquals(EXPECTED_TWO, actualBookDtos.size());
        assertEquals(Arrays.asList(testResponseDto, testResponseDto2), actualBookDtos);
        verify(bookRepository, atMostOnce()).findAll(pageable);
        verify(bookMapper, atMostOnce()).toDto(testBook);
        verify(bookMapper, atMostOnce()).toDto(testBook2);
    }

    @Test
    @DisplayName("""
            Test the getBookDtosByCategoryId() method\s
            should return a list of BookDtoWithoutCategoryIds\s
            for a given category ID""")
    public void getBookDtosByCategoryId_WithValidCategoryId_ReturnsListOfDto() {
        Long categoryId = VALID_ID;
        List<Book> books = List.of(testBook, testBook2);
        when(bookRepository.findByCategoriesId(categoryId, pageable))
                .thenReturn(new PageImpl<>(books, pageable, books.size()));
        when(bookMapper.toDtoWithoutCategories(testBook)).thenReturn(
                new BookDtoWithoutCategoryIds());
        when(bookMapper.toDtoWithoutCategories(testBook2)).thenReturn(
                new BookDtoWithoutCategoryIds());

        List<BookDtoWithoutCategoryIds> actualBookDtos = bookService.getBookDtosByCategoryId(
                categoryId, pageable);

        assertNotNull(actualBookDtos);
        assertEquals(EXPECTED_TWO, actualBookDtos.size());
        verify(bookRepository, atMostOnce()).findByCategoriesId(
                categoryId, pageable);
        verify(bookMapper, atMostOnce()).toDtoWithoutCategories(testBook);
        verify(bookMapper, atMostOnce()).toDtoWithoutCategories(testBook2);
    }

    private void mockMapperToModelAndSave() {
        when(bookMapper.toModel(testRequestDto)).thenReturn(testBook);
        when(bookRepository.save(testBook)).thenReturn(testBook);
    }

    private void verifyInteractionsForSave() {
        verify(bookMapper, atMostOnce()).toModel(testRequestDto);
        verify(bookRepository, atMostOnce()).save(testBook);
        verify(bookMapper, atMostOnce()).toDto(testBook);
    }
}
