package com.example.bookstoreapplication.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.book.BookRepository;
import com.example.bookstoreapplication.repository.book.BookSpecificationBuilder;
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
    private static Pageable pageable;
    private Book testBook;
    private Book testBook2;
    private CreateBookRequestDto testRequestDto;
    private BookDto testResponseDto;
    private BookDto testResponseDto2;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder specificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
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
        verify(bookRepository, times(1)).findById(VALID_ID);
        verify(bookMapper, times(1)).toDto(testBook);
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
                new PageImpl<>(Arrays.asList(testBook, testBook2), pageable, 2));
        when(bookMapper.toDto(testBook)).thenReturn(testResponseDto);
        when(bookMapper.toDto(testBook2)).thenReturn(testResponseDto2);

        List<BookDto> actualBookDtos = bookService.findAll(pageable);

        assertNotNull(actualBookDtos);
        assertEquals(2, actualBookDtos.size());
        assertEquals(Arrays.asList(testResponseDto, testResponseDto2), actualBookDtos);
        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(1)).toDto(testBook);
        verify(bookMapper, times(1)).toDto(testBook2);
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

        // when
        List<BookDtoWithoutCategoryIds> actualBookDtos = bookService.getBookDtosByCategoryId(
                categoryId, pageable);

        // then
        assertNotNull(actualBookDtos);
        assertEquals(2, actualBookDtos.size());
        verify(bookRepository, times(1)).findByCategoriesId(
                categoryId, pageable);
        verify(bookMapper, times(1)).toDtoWithoutCategories(testBook);
        verify(bookMapper, times(1)).toDtoWithoutCategories(testBook2);
    }

    private void mockMapperToModelAndSave() {
        when(bookMapper.toModel(testRequestDto)).thenReturn(testBook);
        when(bookRepository.save(testBook)).thenReturn(testBook);
    }

    private void verifyInteractionsForSave() {
        verify(bookMapper, times(1)).toModel(testRequestDto);
        verify(bookRepository, times(1)).save(testBook);
        verify(bookMapper, times(1)).toDto(testBook);
    }
}
