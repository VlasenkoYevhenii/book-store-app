package com.example.bookstoreapplication.service;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapplication.dto.book.BookSearchParameters;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.example.bookstoreapplication.exception.EntityNotFoundException;
import com.example.bookstoreapplication.mapper.BookMapper;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.book.BookRepository;
import com.example.bookstoreapplication.repository.book.BookSpecificationBuilder;
import com.example.bookstoreapplication.service.book.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final String EXCEPTION = "Failed to get book by id=";
    private static final Integer GREAT_GATSBY_ID = 0;
    private static final Integer PRIDE_AND_PREJUDICE_ID = 1;
    private static final Integer BOOK_1984_ID = 2;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    private static final Long VALID_BOOK_ID = 1L;
    private static final Long INVALID_BOOK_ID = 20L;
    private static final Long VALID_CATEGORY_ID = 1L;
    private static final Integer ONE_TIME = 1;
    private static List<Book> books;
    private static List<BookDto> bookDtos;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder specificationBuilder;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    void setUp() {
        Book greatGatsby = new Book()
                .setTitle("The Great Gatsby")
                .setAuthor("F. Scott Fitzgerald")
                .setIsbn("9780743273565")
                .setPrice(BigDecimal.valueOf(11))
                .setDescription("The story of the fabulously wealthy Jay Gatsby")
                .setCoverImage("https://example.com/book1-cover-image.jpg");
        Book prideAndPrejudice = new Book()
                .setTitle("Pride and Prejudice")
                .setAuthor("Jane Austen")
                .setIsbn("9780141439518")
                .setPrice(BigDecimal.valueOf(20))
                .setDescription("A romantic novel")
                .setCoverImage("https://example.com/book2-cover-image.jpg");
        Book book1984 = new Book()
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setIsbn("9780451524935")
                .setPrice(BigDecimal.valueOf(15))
                .setDescription("A dystopian social science fiction novel")
                .setCoverImage("https://example.com/book3-cover-image.jpg");

        BookDto greatGatsbyDto = new BookDto()
                .setTitle(greatGatsby.getTitle())
                .setAuthor(greatGatsby.getAuthor())
                .setPrice(greatGatsby.getPrice())
                .setIsbn(greatGatsby.getIsbn())
                .setDescription(greatGatsby.getDescription())
                .setCoverImage(greatGatsby.getCoverImage());
        BookDto prideAndPrejudiceDto = new BookDto()
                .setTitle(prideAndPrejudice.getTitle())
                .setAuthor(prideAndPrejudice.getAuthor())
                .setPrice(prideAndPrejudice.getPrice())
                .setIsbn(prideAndPrejudice.getIsbn())
                .setDescription(prideAndPrejudice.getDescription())
                .setCoverImage(prideAndPrejudice.getCoverImage());
        BookDto book1984Dto = new BookDto()
                .setTitle(book1984.getTitle())
                .setAuthor(book1984.getAuthor())
                .setPrice(book1984.getPrice())
                .setIsbn(book1984.getIsbn())
                .setDescription(book1984.getDescription())
                .setCoverImage(book1984.getCoverImage());

        books = List.of(greatGatsby, prideAndPrejudice, book1984);
        bookDtos = List.of(greatGatsbyDto, prideAndPrejudiceDto, book1984Dto);
    }

    @Test
    @DisplayName("Verify save() method with correct requestDto")
    void saveBook_ValidBookRequest_ReturnBookDto() {
        CreateBookRequestDto theHobbitRequestDto = new CreateBookRequestDto()
                .setTitle("The Hobbit")
                .setAuthor("J.R.R. Tolkien")
                .setPrice(BigDecimal.valueOf(15))
                .setIsbn("9780451524930");
        Book theHobbit = new Book()
                .setTitle(theHobbitRequestDto.getTitle())
                .setAuthor(theHobbitRequestDto.getAuthor())
                .setPrice(theHobbitRequestDto.getPrice())
                .setIsbn(theHobbitRequestDto.getIsbn());
        BookDto theHobbitDto = new BookDto()
                .setTitle(theHobbit.getTitle())
                .setAuthor(theHobbit.getAuthor())
                .setIsbn(theHobbit.getIsbn())
                .setPrice(theHobbit.getPrice());

        when(bookMapper.toModel(theHobbitRequestDto)).thenReturn(theHobbit);
        when(bookRepository.save(theHobbit)).thenReturn(theHobbit);
        when(bookMapper.toDto(theHobbit)).thenReturn(theHobbitDto);

        BookDto actual = bookServiceImpl.save(theHobbitRequestDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(theHobbitDto, actual);
        verify(bookRepository, times(ONE_TIME)).save(theHobbit);
    }

    @Test
    @DisplayName("Verify findAll() method")
    void findAllBooks_ValidPageable_ReturnThreeBooks() {
        PageImpl<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE)))
                .thenReturn(bookPage);
        when(bookMapper.toDto(books.get(GREAT_GATSBY_ID)))
                .thenReturn(bookDtos.get(GREAT_GATSBY_ID));
        when(bookMapper.toDto(books.get(PRIDE_AND_PREJUDICE_ID)))
                .thenReturn(bookDtos.get(PRIDE_AND_PREJUDICE_ID));
        when(bookMapper.toDto(books.get(BOOK_1984_ID)))
                .thenReturn(bookDtos.get(BOOK_1984_ID));

        List<BookDto> expected = List.of(bookDtos.get(GREAT_GATSBY_ID),
                bookDtos.get(PRIDE_AND_PREJUDICE_ID), bookDtos.get(BOOK_1984_ID));
        List<BookDto> actual = bookServiceImpl
                .findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        Assertions.assertEquals(3, actual.size());
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, times(ONE_TIME))
                .findAll(PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
    }

    @Test
    @DisplayName("Verify getBookDtoById() method with correct bookId")
    void findBookById_ValidBookId_ReturnBookDto() {
        when(bookRepository
                .findById(VALID_BOOK_ID))
                .thenReturn(Optional.ofNullable(books.get(PRIDE_AND_PREJUDICE_ID)));
        when(bookMapper.toDto(books.get(PRIDE_AND_PREJUDICE_ID)))
                .thenReturn(bookDtos.get(PRIDE_AND_PREJUDICE_ID));

        BookDto actual = bookServiceImpl.getById(VALID_BOOK_ID);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bookDtos.get(PRIDE_AND_PREJUDICE_ID), actual);
        verify(bookRepository, times(ONE_TIME)).findById(VALID_BOOK_ID);
    }

    @Test
    @DisplayName("Verify getBookDtoById() method with invalid bookId")
    void findBookById_InvalidBookId_ThrowException() {
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookServiceImpl.getById(INVALID_BOOK_ID));

        String expected = EXCEPTION + INVALID_BOOK_ID;
        String actual = exception.getMessage();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, times(ONE_TIME)).findById(INVALID_BOOK_ID);
    }

    @Test
    @DisplayName("Verify updateBook() method with correct requestDto")
    void updateBook_ValidBookRequest_ReturnBookDto() {
        CreateBookRequestDto animalFarmRequestDto = new CreateBookRequestDto()
                .setTitle("Animal Farm")
                .setAuthor("George Orwell")
                .setPrice(BigDecimal.valueOf(12))
                .setIsbn("9780451524956");

        Book animalFarm = new Book()
                .setId(VALID_BOOK_ID)
                .setTitle(animalFarmRequestDto.getTitle())
                .setAuthor(animalFarmRequestDto.getAuthor())
                .setPrice(animalFarmRequestDto.getPrice())
                .setIsbn(animalFarmRequestDto.getIsbn());

        BookDto animalFarmDto = new BookDto()
                .setId(VALID_BOOK_ID)
                .setTitle(animalFarm.getTitle())
                .setAuthor(animalFarm.getAuthor())
                .setPrice(animalFarm.getPrice())
                .setIsbn(animalFarm.getIsbn());

        when(bookMapper.toModel(animalFarmRequestDto)).thenReturn(animalFarm);
        when(bookRepository.save(animalFarm)).thenReturn(animalFarm);
        when(bookMapper.toDto(animalFarm)).thenReturn(animalFarmDto);

        BookDto actual = bookServiceImpl.updateBook(VALID_BOOK_ID, animalFarmRequestDto);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(animalFarmDto, actual);
        verify(bookMapper, times(ONE_TIME)).toModel(animalFarmRequestDto);
        verify(bookRepository, times(ONE_TIME)).save(animalFarm);
        verify(bookMapper, times(ONE_TIME)).toDto(animalFarm);
    }


    @Test
    @DisplayName("Verify getBookDtosByParameters() method with correct params")
    void getBooksByParams_ValidParams_ReturnOneBook() {
        BookSearchParameters bookSearchParametersDto
                        = new BookSearchParameters(new String[]{"1984"},
                                new String[]{"George Orwell"});

        when(specificationBuilder
                .build(bookSearchParametersDto)).thenReturn(Specification.where(null));
        when(bookRepository
                .findAll(Specification.where(null)))
                .thenReturn(List.of(books.get(BOOK_1984_ID)));
        when(bookMapper.toDto(books.get(BOOK_1984_ID)))
                .thenReturn(bookDtos.get(BOOK_1984_ID));

        List<BookDto> expected = List.of(bookDtos.get(BOOK_1984_ID));
        List<BookDto> actual = bookServiceImpl.search(bookSearchParametersDto);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, times(ONE_TIME)).findAll(Specification.where(null));
    }

    @Test
    @DisplayName("Verify getBookDtosByCategoryId() method with correct categoryId")
    void getBooksByCategoryId_ValidCategoryId_ReturnOneBook() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(books.get(GREAT_GATSBY_ID)), pageable, 1);
        BookDtoWithoutCategoryIds greatGatsbyDto = new BookDtoWithoutCategoryIds()
                .setTitle(books.get(GREAT_GATSBY_ID).getTitle())
                .setAuthor(books.get(GREAT_GATSBY_ID).getAuthor())
                .setPrice(books.get(GREAT_GATSBY_ID).getPrice())
                .setIsbn(books.get(GREAT_GATSBY_ID).getIsbn())
                .setDescription(books.get(GREAT_GATSBY_ID).getDescription())
                .setCoverImage(books.get(GREAT_GATSBY_ID).getCoverImage());

        when(bookRepository.findByCategoriesId(VALID_CATEGORY_ID, pageable))
                .thenReturn(bookPage);
        when(bookMapper.toDtoWithoutCategories(books.get(GREAT_GATSBY_ID)))
                .thenReturn(greatGatsbyDto);

        List<BookDtoWithoutCategoryIds> expected = List.of(greatGatsbyDto);
        List<BookDtoWithoutCategoryIds> actual
                = bookServiceImpl.getBookDtosByCategoryId(VALID_CATEGORY_ID, pageable);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, times(ONE_TIME))
                .findByCategoriesId(VALID_CATEGORY_ID, pageable);
    }
}
