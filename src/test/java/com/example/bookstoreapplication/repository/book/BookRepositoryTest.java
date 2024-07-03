package com.example.bookstoreapplication.repository.book;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bookstoreapplication.model.Book;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;
    private static final int VALID_PRICE = 100;
    private static final Long TEST_CATEGORY_ID = 333L;
    private static final Long INVALID_TEST_CATEGORY_ID = 444L;
    private static Pageable pageable;
    private static Book game_of_thrones;
    private static Book lord_of_the_rings;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll() {
        pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        lord_of_the_rings = new Book()
                .setTitle("The lord of the rings")
                .setAuthor("Tolkien")
                .setIsbn("9876543219874")
                .setPrice(BigDecimal.valueOf(VALID_PRICE));
        game_of_thrones = new Book()
                .setTitle("Game of Thrones")
                .setAuthor("George R.R. Martin")
                .setIsbn("4564564564561")
                .setPrice(BigDecimal.valueOf(VALID_PRICE));
    }

    @Test
    @Sql(scripts = {
            "classpath:database/book/save_lotr_book_to_table.sql",
            "classpath:database/book/save_g-o-t_book_to_table.sql",
            "classpath:database/book/insert_categories_to_table.sql",
            "classpath:database/book/add_category_to_books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Verify findByCategoriesId() method")
    void findByCategoriesId_AllValidConditions_ReturnsPageOfBooks() {
        Page<Book> expected = new PageImpl<>(List.of(lord_of_the_rings, game_of_thrones));
        int expectedElementsCount = expected.getContent().size();
        String expectedTitle = lord_of_the_rings.getTitle();

        Page<Book> actual = bookRepository.findByCategoriesId(TEST_CATEGORY_ID, pageable);

        assertThat(actual.getNumberOfElements()).isEqualTo(expectedElementsCount);
        assertThat(actual.getContent().get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @Sql(scripts = {
            "classpath:database/book/save_lotr_book_to_table.sql",
            "classpath:database/book/save_g-o-t_book_to_table.sql",
            "classpath:database/book/insert_categories_to_table.sql",
            "classpath:database/book/add_category_to_books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Verify findByCategoriesId() method""")
    void findByCategoriesId_NonExistentCategoryId_ReturnsEmptyPage() {
        Page<Book> expected = new PageImpl<>(Collections.emptyList());
        Page<Book> actual = bookRepository.findByCategoriesId(INVALID_TEST_CATEGORY_ID, pageable);

        assertThat(expected.getContent().size()).isEqualTo(actual.getNumberOfElements());
    }
}
