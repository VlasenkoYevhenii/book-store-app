package com.example.bookstoreapplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstoreapplication.dto.book.BookDto;
import com.example.bookstoreapplication.dto.book.BookSearchParameters;
import com.example.bookstoreapplication.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String API = "/api/books";
    private static final String SLASH = "/";
    private static final Long LOTR_ID = 111L;
    private static List<BookDto> bookDtoList;
    private static BookDto gameOfThronesDto;
    private static BookDto lordOfTheRingsDto;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        gameOfThronesDto = new BookDto()
                .setTitle("Game of Thrones")
                .setAuthor("George R.R. Martin")
                .setIsbn("978-617-7682-22-5")
                .setPrice(BigDecimal.valueOf(133));
        lordOfTheRingsDto = new BookDto()
                .setTitle("The lord of the rings")
                .setAuthor("Tolkien")
                .setIsbn("9876543219876")
                .setPrice(BigDecimal.valueOf(133));

        bookDtoList = new ArrayList<>();
    }

    @Test
    @WithMockUser(username = "user", roles = USER)
    @DisplayName("""
            Verify getAll() method with role = USER""")
    @Sql(scripts = {
            "classpath:database/book/cleanup_tables.sql",
            "classpath:database/book/save_g-o-t_book_to_table.sql",
            "classpath:database/book/save_lotr_book_to_table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_AllValidConditions_ReturnsListOfDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get(API)
                ).andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<BookDto> actual = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        bookDtoList = List.of(gameOfThronesDto, lordOfTheRingsDto);

        assertEquals(bookDtoList.size(), actual.size());
        assertEquals(actual.get(0).getTitle(), lordOfTheRingsDto.getTitle());
        assertEquals(actual.get(1).getTitle(), gameOfThronesDto.getTitle());
    }

    @Test
    @WithMockUser(username = "user", roles = USER)
    @DisplayName("""
            Verify getById() with user role = USER""")
    @Sql(scripts = {
            "classpath:database/book/cleanup_tables.sql",
            "classpath:database/book/save_lotr_book_to_table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBookById_AllValidConditions_ReturnBookDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(API + SLASH + LOTR_ID))
                .andReturn();

        BookDto expected = lordOfTheRingsDto;

        BookDto actual =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        BookDto.class);
        assertNotNull(actual);
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    @WithMockUser(username = "admin", roles = ADMIN)
    @DisplayName("""
            Verify new book creation""")
    @Sql(scripts = "classpath:database/book/insert_categories_to_table.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createBook_AllValidConditions_ReturnsBookDto() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto()
                .setTitle("Game of Thrones")
                .setAuthor("George R.R. Martin")
                .setIsbn("978-617-7682-22-5")
                .setPrice(BigDecimal.valueOf(133))
                .setCategoryIds(Set.of(333L));

        BookDto expected = gameOfThronesDto
                .setCategoryIds(requestDto.getCategoryIds());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post(API)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andReturn();

        BookDto actual =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        BookDto.class);
        assertNotNull(actual);
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getIsbn(), actual.getIsbn());
    }

    @Test
    @WithMockUser(username = "user", roles = USER)
    @DisplayName("""
            Verify search() method""")
    @Sql(scripts = {
            "classpath:database/book/cleanup_tables.sql",
            "classpath:database/book/save_lotr_book_to_table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void search_AllValidConditions_ReturnsListOfBookDto() throws Exception {
        BookSearchParameters parametersDto
                = new BookSearchParameters(new String[]{"rings"}, new String[]{"Tolkien"});

        MvcResult mvcResult = mockMvc.perform(get(API + "/search")
                        .param("authors", String.join(",", parametersDto.authors()))
                        .param("titles", String.join(",", parametersDto.titles())))
                .andReturn();

        List<BookDto> expected = List.of(lordOfTheRingsDto);
        List<BookDto> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertEquals(1, actual.size());
        assertEquals(expected.get(0).getTitle(), actual.get(0).getTitle());
    }
}
